package com.bdxpert.awsparamstore.utils;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParametersResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.bdxpert.awsparamstore.utils.CommonConstant.DB_NAME;
import static com.bdxpert.awsparamstore.utils.CommonConstant.DB_PASSWORD;
import static com.bdxpert.awsparamstore.utils.CommonConstant.DB_REPLICA_URI;
import static com.bdxpert.awsparamstore.utils.CommonConstant.DB_URI_PARAMS;
import static com.bdxpert.awsparamstore.utils.CommonConstant.DB_USER;

/**
 * @author Sanjiv
 * @date 10/9/2020
 */

/**
 * @class StoreFetcher
 */
public class StoreFetcher {
    private final static Logger logger = LoggerFactory.getLogger(StoreFetcher.class);
    private final String storePrefix;


    public StoreFetcher(String storePrefix) {
        this.storePrefix = storePrefix;
        setParameters();
    }

    /**
     * Sets system properties for application
     *
     * @return
     */
    public Boolean setParameters() {
        try {
            List<String> paramList = new ArrayList<>();
            paramList.add(storePrefix + "/" + DB_NAME);
            paramList.add(storePrefix + "/" + DB_USER);
            paramList.add(storePrefix + "/" + DB_PASSWORD);
            paramList.add(storePrefix + "/" + DB_URI_PARAMS);
            paramList.add(storePrefix + "/" + DB_REPLICA_URI);
            // initialize client
            AWSSimpleSystemsManagement client = AWSSimpleSystemsManagementClientBuilder.standard().build();
            GetParametersRequest request = new GetParametersRequest();

            request.withNames(paramList).setWithDecryption(Boolean.TRUE);

            GetParametersResult result = client.getParameters(request);

            storeIntoAppSystemProperty(result, storePrefix + "/");

            return true;
        } catch (Exception requestException) {
            logger.info("Exception occurred while requesting properties: {}", requestException);
        }
        return false;
    }

    /**
     * Save properties to application system properties
     *
     * @param result result from parameter store
     * @param prefix
     */
    private void storeIntoAppSystemProperty(GetParametersResult result, String prefix) {
        result.getParameters().forEach(paramWithPrefix -> {
            String name = paramWithPrefix.getName().replace(prefix, "").trim();
            String value = paramWithPrefix.getValue();
            System.setProperty(name, value);
        });
    }
}
