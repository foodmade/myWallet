package com.mywallet.commom.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * HibernateJpaConfig
 *
 * @author linapex
 */
@Configuration
@EntityScan(basePackages = "com.mywallet.model")
@EnableJpaRepositories(basePackages = "com.mywallet.repository")
@EnableTransactionManagement
public class HibernateJpaConfig {

}