package com.apollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

@SpringBootApplication(exclude = {FlywayAutoConfiguration.class})
public class ApolloApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApolloApiServiceApplication.class, args);
    }
}
