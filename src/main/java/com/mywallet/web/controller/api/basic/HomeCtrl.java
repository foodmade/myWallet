package com.mywallet.web.controller.api.basic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mywallet.commom.wallet.LightWallet;
import com.mywallet.model.Feedback;
import com.mywallet.model.Users;
import com.mywallet.model.Wallet;
import com.mywallet.repository.FeedbackRepository;
import com.mywallet.repository.UsersRepository;
import com.mywallet.repository.WalletRepository;
import com.mywallet.util.EthplorerApi;
import com.mywallet.util.ExchangeApi;
import com.mywallet.util.FormatUtil;
import com.mywallet.web.controller.api.call.MyToken;
import com.mywallet.web.controller.api.suport.BaseController;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * HomeCtrl
 *
 * @author linapex
 */
@RestController
@RequestMapping("/api")
public class HomeCtrl extends BaseController {

    @RequestMapping("/home")
    public JSONObject doHome(HttpSession session) {
        Users users = getCurrentUsers(session);
        if (null == users) {
            return newJson("-1", "您当前还未登录！");
        }

        JSONObject jsonObject = new JSONObject();

        List<Wallet> wallets = walletRepository.findByUid(users.getId());
        jsonObject.put("walletSize", wallets.size());
        if (!wallets.isEmpty()) {
            //得到第一个钱包地址
            String address = wallets.get(0).getAddress();

            //得到钱包eth余额
            BigInteger weiBalance = LightWallet.getBalance(address);
            Double ethBalance = FormatUtil.formatEth(weiBalance + "");
            jsonObject.put("balance", ethBalance);
            //计算出eth的人民币金额
            JSONObject tickerJsonObject = ExchangeApi.getInstance().getTicker();
            double rate = ExchangeApi.getInstance().exchangeRate();
            double lastCny = tickerJsonObject.getJSONObject("ticker").getBigDecimal("last").multiply(new BigDecimal(rate)).doubleValue();
            double cnyBalance = jsonObject.getBigDecimal("balance").multiply(new BigDecimal(lastCny)).doubleValue();
            jsonObject.put("cnyBalance", cnyBalance);
            jsonObject.put("address", address);
            jsonObject.put("name", wallets.get(0).getName());
        }
        return newJson(jsonObject);
    }

    @RequestMapping("/register")
    public JSONObject doRegister(HttpSession session, String username, String password) {
        Users users = usersRepository.findByUsernameAndPassword(username, password);
        if (null != users) {
            return newJson("-1", "帐号已存在！");
        }

        users = new Users();
        users.setUsername(username);
        users.setPassword(password);
        users.setStatus(1);
        users.setGmtCreate(new Date());
        users.setGmtModified(new Date());
        usersRepository.save(users);

        //自动登录
        session.setAttribute("users", users);
        return newJson("");
    }

    @RequestMapping("/login")
    public JSONObject doLogin(HttpSession session, String username, String password) {
        Users users = usersRepository.findByUsernameAndPassword(username, password);
        if (null == users) {
            return newJson("-1", "帐号或密码错误！");
        }
        session.setAttribute("users", users);
        return newJson("");
    }

    @RequestMapping("/feedback")
    public JSONObject doFeedback(HttpSession session, String content) {
        Users users = getCurrentUsers(session);
        if (null == users) {
            return newJson("-1", "您当前还未登录！");
        }
        Feedback feedback = new Feedback();
        feedback.setUid(users.getId());
        feedback.setUsername(users.getUsername());
        feedback.setContent(content);
        feedback.setGmtCreate(new Date());
        feedback.setGmtModified(new Date());
        feedbackRepository.save(feedback);
        session.setAttribute("users", users);
        return newJson("");
    }

    @RequestMapping("/market")
    public JSONObject doMarket() {
        JSONObject jsonObject = ExchangeApi.getInstance().getTicker();

        double rate = ExchangeApi.getInstance().exchangeRate();
        double lastCny = jsonObject.getJSONObject("ticker").getBigDecimal("last").multiply(new BigDecimal(rate)).doubleValue();
        double highCny = jsonObject.getJSONObject("ticker").getBigDecimal("high").multiply(new BigDecimal(rate)).doubleValue();
        double lowCny = jsonObject.getJSONObject("ticker").getBigDecimal("low").multiply(new BigDecimal(rate)).doubleValue();

        jsonObject.getJSONObject("ticker").put("lastCny", lastCny);
        jsonObject.getJSONObject("ticker").put("highCny", highCny);
        jsonObject.getJSONObject("ticker").put("lowCny", lowCny);
        return newJson(jsonObject);
    }

    @RequestMapping("/generateWallet")
    public JSONObject doGenerateWallet(HttpSession session, String password) throws Exception {
        if (Strings.isBlank(password) || password.length() <= passwordLength) {
            return newJson("-1", "密码长度必须为6位以上");
        }

        Users users = getCurrentUsers(session);
        if (null == users) {
            return newJson("-1", "您当前还未登录！");
        }

        String[] dataArr = LightWallet.createNewWallet(password);
        Credentials credentials = LightWallet.openWallet(password, dataArr[0]);

        String privateKey = Numeric.toHexStringNoPrefix(credentials.getEcKeyPair().getPrivateKey());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("walletName", dataArr[0]);
        jsonObject.put("privateKey", privateKey);
        jsonObject.put("address", credentials.getAddress());

        Wallet wallet = new Wallet();
        wallet.setUid(users.getId());
        wallet.setName(dataArr[0]);
        wallet.setAddress(credentials.getAddress());
        wallet.setPassword(password);
        wallet.setPrivateKey(privateKey);
        wallet.setKeyStore(dataArr[1]);
        wallet.setGmtCreate(new Date());
        wallet.setGmtModified(new Date());
        walletRepository.save(wallet);

        return newJson(jsonObject);
    }

    @RequestMapping(value = "/download/{walletName}")
    @ResponseBody
    public void download(HttpServletResponse response, @PathVariable String walletName) throws Exception {
        File walletPath = new File(LightWallet.getWallet(walletName));
        if (!walletPath.exists()) {
            throw new FileNotFoundException();
        }

        InputStream input = FileUtils.openInputStream(walletPath);
        byte[] data = IOUtils.toByteArray(input);
        String fileName = URLEncoder.encode(walletPath.getName(), "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
        IOUtils.closeQuietly(input);
    }

    @RequestMapping("/listWallet")
    public JSONObject doListWallet(HttpSession session) throws Exception {
        Users users = getCurrentUsers(session);
        if (null == users) {
            return newJson("-1", "您当前还未登录！");
        }

        List<Wallet> wallets = walletRepository.findByUid(users.getId());
        List<JSONObject> list = Lists.newArrayList();

        //计算出eth的人民币金额
        JSONObject tickerJsonObject = ExchangeApi.getInstance().getTicker();
        double rate = ExchangeApi.getInstance().exchangeRate();
        double lastCny = tickerJsonObject.getJSONObject("ticker").getBigDecimal("last").multiply(new BigDecimal(rate)).doubleValue();

        for (Wallet wallet : wallets) {
            JSONObject walletObject = new JSONObject();
            walletObject.put("id", wallet.getId());
            walletObject.put("name", wallet.getName().hashCode());
            String address = wallet.getAddress();
            String index = address.substring(0, 10);
            String last = address.substring(39);
            walletObject.put("address", String.format("%s...%s", index, last));
            walletObject.put("oldAddress", address);

            //得到钱包eth余额
            BigInteger weiBalance = LightWallet.getBalance(wallet.getAddress());
            Double ethBalance = FormatUtil.formatEth(weiBalance + "");
            walletObject.put("balance", ethBalance);

            double cnyBalance = walletObject.getBigDecimal("balance").multiply(new BigDecimal(lastCny)).doubleValue();
            walletObject.put("cnyBalance", cnyBalance);
            list.add(walletObject);
        }

        return newJson(list);
    }

    @RequestMapping("/delWallet/{id}")
    public JSONObject doDelWallet(HttpSession session, @PathVariable int id) throws Exception {
        Users users = getCurrentUsers(session);
        if (null == users) {
            return newJson("-1", "您当前还未登录！");
        }
        Wallet wallet = walletRepository.getOne(id);
        if (!Objects.equals(wallet.getUid(), users.getId())) {
            return newJson("-1", "错误的操作！");
        }
        walletRepository.deleteById(id);
        return newJson("");
    }

    @RequestMapping("/wallet/{address}")
    public JSONObject doGetAddressTransactions(@PathVariable String address) throws Exception {
        JSONArray jsonArray = EthplorerApi.getInstance().getAddressTransactions(address);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String to = object.getString("to");
            String index = to.substring(0, 10);
            String last = to.substring(39);
            object.put("to", String.format("%s...%s", index, last));

            boolean isFrom = StringUtils.equalsIgnoreCase(address, object.getString("from"));
            object.put("value", (isFrom ? "-" : "+") + object.get("value"));
        }
        return newJson(jsonArray);
    }

    @RequestMapping("/transafer")
    public JSONObject doTransafer(HttpSession session, String sendAddress, String receiveAddress, String amount) throws Exception {
        Users users = getCurrentUsers(session);
        if (null == users) {
            return newJson("-1", "您当前还未登录！");
        }

        //1、判断转账的账户是否是此用户下的钱包
        Wallet wallet = walletRepository.findByAddress(sendAddress);
        if (null == wallet) {
            return newJson("-1", "此钱包不存在！");
        }
        if (!Objects.equals(wallet.getUid(), users.getId())) {
            return newJson("-1", "错误的操作！");
        }
        //2、取出钱包私钥，调用转账接口
        try {
            Credentials credentials = Credentials.create(wallet.getPrivateKey());
            LightWallet.transafer(credentials, receiveAddress, amount);
        } catch (Exception e) {
        }
        return newJson("");
    }

    private static Boolean bol = false;
    /**
     * 主账户余额：952.000000000000000000
     * 次账户余额：48.000000000000000000
     * @return
     * @throws IOException
     * @throws CipherException
     */
    @RequestMapping(value = "/transTest",method = RequestMethod.GET)
    public JSONObject transTest() throws Exception {

        String address = "0x10bb629008337b807bfb47125a34a35036e31834";

        String account1 = "0x14a5f782b72eb2dde4fb745e61d85702d9d1f3ed";

        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version :"+clientVersion);

        Credentials credentials = WalletUtils.loadCredentials("chen19960119", "D:\\block\\keystore\\UTC--2019-11-04T02-08-34.977665400Z--7e30710300837d0f174eaa896638bdffaaa2c2f0");
//        MyToken myToken = MyToken.deploy(web3,credentials,new DefaultGasProvider(),BigInteger.valueOf(100),BigInteger.valueOf(100000),"BBT",BigInteger.valueOf(10),"T").send();

        MyToken myToken = MyToken.load(address,web3,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT);

        System.out.println("合约部署完毕 状态:" + myToken.isValid() + " 地址：" + myToken.getContractAddress());

        if(!bol){
            bol = true;
            //监听event事件
            new Thread(() -> {
                Event event = new Event("transfer",
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
                EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST, address);
                filter.addSingleTopic(EventEncoder.encode(event));

                System.out.println("开启监听event事件.....");

                web3.transactionFlowable().subscribe(log -> {
                    System.out.println("监听到事件：\n");

                    System.out.println(JSON.toJSONString(log));
                });
            }).start();
        }


        CompletableFuture<TransactionReceipt> future = myToken.transfer(account1,BigInteger.valueOf(1200)).sendAsync();

        new Thread(() -> {
            try {
                TransactionReceipt receipt = future.get();
                System.out.println("转账完成 JSON:" + JSON.toJSONString(receipt));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();


        System.out.println("主账户余额：" + toDecimal(2,myToken.getMasterBalance().send()));
        System.out.println("次账户余额：" + toDecimal(2,myToken.getBalance(account1).send()));

        return newJson("success");
    }

    public static String toDecimal(int decimal,BigInteger integer){
//		String substring = str.substring(str.length() - decimal);
        StringBuffer sbf = new StringBuffer("1");
        for (int i = 0; i < decimal; i++) {
            sbf.append("0");
        }
        String balance = new BigDecimal(integer).divide(new BigDecimal(sbf.toString()), 18, BigDecimal.ROUND_DOWN).toPlainString();
        return balance;
    }

    int passwordLength = 5;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    FeedbackRepository feedbackRepository;
}
