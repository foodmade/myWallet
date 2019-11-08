package com.mywallet.util;

import com.alibaba.fastjson.JSONObject;
import com.mywallet.exception.ServiceException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * ExchangeApi
 *
 * @author linapex
 */
public class ExchangeApi {

    public JSONObject getTicker() {
        JSONObject jsonObject = null;
        try {
            String url = "https://www.okex.com/api/v1/ticker.do?symbol=eth_usdt";
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                logger.error("okHttp is request error");
                throw new ServiceException("调用getAddressTransactions出现异常");
            }
            String result = response.body().string();
            jsonObject = JSONObject.parseObject(result);
//            Double last = jsonObject.getJSONObject("ticker").getDouble("last");
//            System.out.println("eth最新价格：" + last);
        } catch (Exception e) {
            logger.error("getTicker异常", e);
        }
        return jsonObject;
    }

    public double ethCny(String json) {
        double value = 0;
        try {
            value = JSONObject.parseObject(json).getJSONObject("ticker").getBigDecimal("last").multiply(new BigDecimal(exchangeRate())).doubleValue();
        } catch (Exception e) {
        }
        return value;
    }

    public double exchangeRate() {
        double value = 0;
        try {
            String url = "https://www.okex.com/api/v1/exchange_rate.do";
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            value = JSONObject.parseObject(result).getDouble("rate");
        } catch (Exception e) {
        }
        return value;
    }

    OkHttpClient client;
    Logger logger = LoggerFactory.getLogger(ExchangeApi.class);

    public ExchangeApi() {
        //初始化链接
        client = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new ExchangeApi.TrustAllHostnameVerifier())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static ExchangeApi getInstance() {
        return ExchangeApi.LazyHolder.singletonInstatnce;
    }

    private static class LazyHolder {
        private static final ExchangeApi singletonInstatnce = new ExchangeApi();
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
            sc.init(null, new TrustManager[]{new ExchangeApi.TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }
}
