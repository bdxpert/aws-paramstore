package com.bdxpert.awsparamstore;

import com.bdxpert.awsparamstore.utils.StoreInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AwsParamstoreApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(AwsParamstoreApplication.class)
                .initializers(new StoreInitializer())
                .run(args);
    }

}
