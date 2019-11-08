package com.mywallet.commom.wallet;

import com.mywallet.exception.TransferException;
import com.mywallet.util.FormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * 轻钱包
 *
 * @author linapex
 */
public class LightWallet {

    /**
     * 创建一个新的钱包
     *
     * @param password
     * @return
     * @throws Exception
     */
    public static String[] createNewWallet(String password) throws Exception {
        try {
            File file = new File(WALLET_PATH);
            String name = null;
            String json = null;
            if (file.exists()) {
                // al crear el monedero nos retorna el nombre del archivo generado dentro de la carpeta indicada
                name = WalletUtils.generateFullNewWalletFile(password, file);
                // vamos a abrir el monedero y retornar el json generado
                Path path = FileSystems.getDefault().getPath(WALLET_PATH, name);
                byte[] b = java.nio.file.Files.readAllBytes(path);
                json = new String(b);
//                logger.info("{}", Arrays.toString(new String[]{name, json}));
                return new String[]{name, json};
            } else {
                throw new Exception("Invalid WALLET_PATH " + WALLET_PATH);
            }
        } catch (Exception ex) {
            throw ex;
        }
    }


    /**
     * 打开钱包
     *
     * @param password
     * @param walletName
     * @return
     * @throws Exception
     */
    public static Credentials openWallet(String password, String walletName) throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(password, WALLET_PATH + walletName);
        logger.info("私钥:{}", Numeric.toHexStringNoPrefix(credentials.getEcKeyPair().getPrivateKey()));
        logger.info("公钥:{}", Numeric.toHexStringWithPrefixZeroPadded(credentials.getEcKeyPair().getPublicKey(), 64 << 1));
        logger.info("地址:{}", credentials.getAddress());
        return credentials;
    }

    static String openWalletGetPrivatePassword(String password, String walletName) throws Exception {
        Credentials credentials = WalletUtils.loadCredentials(password, WALLET_PATH + walletName);
        return Numeric.toHexStringNoPrefix(credentials.getEcKeyPair().getPrivateKey());
    }

    /**
     * 查询余额
     *
     * @param address
     * @return
     */
    public static BigInteger getBalance(String address) {
        try {
            EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
            if (ethGetBalance != null) {
                return ethGetBalance.getBalance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void transafer(Credentials credentials, String sendAccount, String money) throws Exception {
        String addressSender = credentials.getAddress();
        System.out.println("发送人地址: " + addressSender);

        System.out.println("接收人地址: " + sendAccount);

        BigDecimal transaferMoney = new BigDecimal(money);
        System.out.println("转账金额: " + transaferMoney);

        sendCoins(credentials, sendAccount, transaferMoney);
    }

    /**
     * 离线转账，效率最高
     *
     * @param credentials
     * @param toAddress
     * @param value
     * @return
     * @throws Exception
     */
    public static String sendCoins(Credentials credentials, String toAddress, BigDecimal value) throws Exception {
        Convert.Unit unit = Convert.Unit.ETHER;
        String tokenValue = String.valueOf(value);
        BigDecimal weiValue = Convert.toWei(tokenValue, unit);
        if (!Numeric.isIntegerValue(weiValue)) {
            throw new UnsupportedOperationException(
                    "Non decimal Wei value provided: " + tokenValue + " " + unit.toString()
                            + " = " + weiValue + " Wei");
        }

        //nonce
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println("nonce:" + nonce);

        //RawTransaction
        RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, Transfer.GAS_PRICE, Transfer.GAS_LIMIT, toAddress, weiValue.toBigIntegerExact());

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);
        System.out.println("hexValue:" + hexValue);

        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = ethSendTransaction.getTransactionHash();
        if (ethSendTransaction.getError().getCode() != 1) {
            System.out.println("error data:" + ethSendTransaction.getError().getData());
            System.out.println("error msg:" + ethSendTransaction.getError().getMessage());
            System.out.println("error code:" + ethSendTransaction.getError().getCode());
            throw new TransferException(ethSendTransaction.getError().getMessage());
        }
        System.out.println("transactionHash:" + transactionHash);
        return transactionHash;
    }

    public static String getWallet(String name) {
        return WALLET_PATH + name;
    }

    static Logger logger = LoggerFactory.getLogger(LightWallet.class);
    static String WALLET_PATH = "D:\\block\\keystore\\";
    static Web3j web3j = Web3JClient.getClient();

}
