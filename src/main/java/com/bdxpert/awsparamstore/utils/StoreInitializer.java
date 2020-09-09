package com.bdxpert.awsparamstore.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by sanjiv on 10/09/2020.
 */

public class StoreInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final Logger logger = LoggerFactory.getLogger(StoreInitializer.class);

    @Override
    public void initialize(ConfigurableApplicationContext ac) {
        try {
            ConfigurableEnvironment env = ac.getEnvironment();
            logger.info("store.prefix: {}", env.getProperty("store.prefix"));
            new StoreFetcher(env.getProperty("store.prefix"));

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
