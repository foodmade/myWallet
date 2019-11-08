package com.mywallet.commom;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AppProperties
 *
 * @author linapex
 */
@ConfigurationProperties
@Component
public class AppProperties implements InitializingBean {

    @Value("${spring.profiles.active}")
    public String active;

    @Value("${basic.startVerfyCode}")
    public boolean startVerfyCode;

    String dev = "dev";
    String prod = "prod";

    public boolean isDev() {
        if (dev.equals(active)) {
            return true;
        }
        return false;
    }

    public boolean isProd() {
        if (prod.equals(active)) {
            return true;
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
