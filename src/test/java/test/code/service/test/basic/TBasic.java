package test.code.service.test.basic;

import com.alibaba.fastjson.JSON;
import com.mywallet.Application;
import com.mywallet.model.Feedback;
import com.mywallet.repository.FeedbackRepository;
import com.mywallet.repository.WalletRepository;
import com.mywallet.web.controller.api.call.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@WebAppConfiguration
@DirtiesContext
@ActiveProfiles("test")
public class TBasic {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    WalletRepository walletRepository;

    @Test
    public void testAddFeedback() {
        Feedback feedback = new Feedback();
        feedback.setContent("123");
        feedback.setUsername("demo");
        feedback.setGmtCreate(new Date());
        feedbackRepository.save(feedback);
    }

    /**
     * [
     *        {
     * 		"constant": false,
     * 		"inputs": [
     *            {
     * 				"internalType": "address",
     * 				"name": "from",
     * 				"type": "address"
     *            },
     *            {
     * 				"internalType": "uint256",
     * 				"name": "tokens",
     * 				"type": "uint256"
     *            },
     *            {
     * 				"internalType": "address",
     * 				"name": "token",
     * 				"type": "address"
     *            },
     *            {
     * 				"internalType": "bytes",
     * 				"name": "data",
     * 				"type": "bytes"
     *            }
     * 		],
     * 		"name": "receiveApproval",
     * 		"outputs": [],
     * 		"payable": false,
     * 		"stateMutability": "nonpayable",
     * 		"type": "function"
     *    }
     * ]
     */

    @Test
    public void testListWallet() {
        logger.info("{}", walletRepository.findByUid(1));
    }

    @Test
    public void testTransToken() throws Exception {
        String contractAddress = "";

        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version :"+clientVersion);


        Credentials credentials = WalletUtils.loadCredentials("chen19960119", "D:\\block\\keystore\\UTC--2019-11-04T02-08-34.977665400Z--7e30710300837d0f174eaa896638bdffaaa2c2f0");

        String address1 = "0x51ca7fc8e60fd7c83859f08b14e5a3b7849ec44a";
        String address2 = "0xc462064e45aa720ff16f0a9bc3cc99b23fe4a7b9";
        String address3 = "0x528b5a0f6950f312e97857370d59f4c623240f1a";

        String account1 = "0x7e30710300837D0F174Eaa896638BDFfaaA2C2f0";
        String account2 = "0x161d09dBda519f55380e9Ead7a60dFD3E8E920AD";   //0.4
        String account3 = "0x9022A1B1Cdd513597446f01563A5D6Fbec1c719B";   //0.0
        String account4 = "0x9022A1B1Cdd513597446f01563A5D6Fbec1c718A";   //0.0

//        TokenERC20 suibeToken = TokenERC20.deploy(web3,credentials,new DefaultGasProvider(),BigInteger.valueOf(10000L),"ABA","aa").send();
//        SUIBEToken fiveToken = SUIBEToken.deploy(web3,credentials,new DefaultGasProvider()).send();
//        ApproveAndCallFallBack callFallBack = ApproveAndCallFallBack.deploy(web3,credentials,new DefaultGasProvider()).send();
//        Owned owned = Owned.deploy(web3,credentials,new DefaultGasProvider()).send();
//        Sample sample = Sample.deploy(web3,credentials,new DefaultGasProvider(),BigInteger.valueOf(10000)).send();
        Token1 token1 = Token1.deploy(web3,credentials,new DefaultGasProvider()).send();

        System.out.println("部署合约完毕");

//        TokenERC20 suibeToken = TokenERC20.load(address1,web3,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
//        SUIBEToken fiveToken = SUIBEToken.load(address2,web3,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);
        Sample sample = Sample.load(address3,web3,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);

        sample.set(BigInteger.valueOf(2000000)).send();

        System.out.println("智能合约地址："+sample.getContractAddress());
        System.out.println("是否有效："+sample.isValid());

        System.out.println("余额：" + toDecimal(2,sample.get().send()));
        //调用智能合约
        //转账
//        TransactionReceipt receipt = suibeToken.transferFrom(account2,account3,BigInteger.valueOf(20)).sendAsync().get();
//        TransactionReceipt receipt = suibeToken.transfer(account4,BigInteger.valueOf(50)).sendAsync().get();
//        TransactionReceipt receipt = fiveToken.transfer(account4,BigInteger.valueOf(100)).send();

//        BigInteger b1 = fiveToken.balanceOf(account1).sendAsync().get();
//        System.out.println("余额1："+ toDecimal(2,b1));
//        BigInteger b2 = suibeToken.balanceOf(account2).sendAsync().get();
//        BigInteger b3 = suibeToken.balanceOf(account3).sendAsync().get();
//        BigInteger b4 = fiveToken.balanceOf(account4).sendAsync().get();
////        suibeToken.approveAndCall()
//        System.out.println("余额2："+ toDecimal(2,b2));
//        System.out.println("余额3："+ toDecimal(2,b3));
//        System.out.println("余额4："+ toDecimal(2,b4));
}




    @Test
    public void testToken1() throws Exception {
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version :"+clientVersion);

        Credentials credentials = WalletUtils.loadCredentials("chen19960119", "D:\\block\\keystore\\UTC--2019-11-04T02-08-34.977665400Z--7e30710300837d0f174eaa896638bdffaaa2c2f0");
        Token1 token1 = Token1.deploy(web3,credentials,new DefaultGasProvider()).send();

        System.out.println("部署成功 状态：" + token1.isValid() + " 地址：" + token1.getContractAddress());
        System.out.println("初始化合约代币余额");
    }

    @Test
    public void testToken3() throws Exception{
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version :"+clientVersion);

        Credentials credentials = WalletUtils.loadCredentials("chen19960119", "D:\\block\\keystore\\UTC--2019-11-04T02-08-34.977665400Z--7e30710300837d0f174eaa896638bdffaaa2c2f0");
        Token2 token3 = Token2.deploy(web3,credentials,new DefaultGasProvider()).send();

        System.out.println("部署成功 状态：" + token3.isValid() + " 地址：" + token3.getContractAddress());
        token3.issue(BigInteger.valueOf(2500000)).send();
        System.out.println("初始化合约代币余额完毕");
        System.out.println("合约初始余额：" + toDecimal(2,token3.getMasterBalance().send()));
    }

    @Test
    public void testERC20() throws Exception {

        String address1 = "0x51ca7fc8e60fd7c83859f08b14e5a3b7849ec44a";

        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version :"+clientVersion);

        Credentials credentials = WalletUtils.loadCredentials("chen19960119", "D:\\block\\keystore\\UTC--2019-11-04T02-08-34.977665400Z--7e30710300837d0f174eaa896638bdffaaa2c2f0");
        TokenERC20 suibeToken = TokenERC20.deploy(web3,credentials,new DefaultGasProvider(),BigInteger.valueOf(10000L),"ABA","aa").send();

        System.out.println("ERC20 Token部署完毕 状态:" + suibeToken.isValid() + "\n 合约地址：" + suibeToken.getContractAddress());


    }

    @Test
    public void testMyToken() throws Exception {

        String address = "0x10bb629008337b807bfb47125a34a35036e31834";

        String account1 = "0x14a5f782b72eb2dde4fb745e61d85702d9d1f3ed";

        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version :"+clientVersion);

        Credentials credentials = WalletUtils.loadCredentials("chen19960119", "D:\\block\\keystore\\UTC--2019-11-04T02-08-34.977665400Z--7e30710300837d0f174eaa896638bdffaaa2c2f0");
//        MyToken myToken = MyToken.deploy(web3,credentials,new DefaultGasProvider(),BigInteger.valueOf(100),BigInteger.valueOf(100000),"BBT",BigInteger.valueOf(10),"T").send();

        MyToken myToken = MyToken.load(address,web3,credentials,Contract.GAS_PRICE,Contract.GAS_LIMIT);

        System.out.println("合约部署完毕 状态:" + myToken.isValid() + " 地址：" + myToken.getContractAddress());

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
    }


    @Test
    public void testCreateCon() throws Exception {
        Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
        Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("version :"+clientVersion);

        Credentials credentials = WalletUtils.loadCredentials("chen19960119", "D:\\block\\keystore\\UTC--2019-11-04T02-08-34.977665400Z--7e30710300837d0f174eaa896638bdffaaa2c2f0");
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
}
