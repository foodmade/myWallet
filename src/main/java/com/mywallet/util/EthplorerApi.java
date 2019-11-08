package com.mywallet.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mywallet.exception.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * EthplorerApi
 *
 * @author linapex
 */
public class EthplorerApi {

    public JSONArray getAddressTransactions(String address) {
        JSONArray jsonArray = null;
        try {
            String url = String.format("https://api.ethplorer.io/getAddressTransactions/%s?apiKey=%s", address, etherscanApiKey);
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                logger.error("okHttp is request error");
                throw new ServiceException("调用getAddressTransactions出现异常");
            }
            jsonArray = JSONObject.parseArray(response.body().string());
            logger.debug("getAddressTransactions {} 返回参数", jsonArray);
        } catch (Exception e) {
            logger.error("getAddressTransactions异常", e);
        }
        return jsonArray;
    }

    OkHttpClient client;
    String etherscanApiKey;
    Logger logger = LoggerFactory.getLogger(EthplorerApi.class);

    public EthplorerApi() {
        etherscanApiKey = "freekey";

        //初始化链接
        client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new EthplorerApi.TrustAllHostnameVerifier())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static EthplorerApi getInstance() {
        return EthplorerApi.LazyHolder.singletonInstatnce;
    }

    private static class LazyHolder {
        private static final EthplorerApi singletonInstatnce = new EthplorerApi();
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new EthplorerApi.TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }
}
