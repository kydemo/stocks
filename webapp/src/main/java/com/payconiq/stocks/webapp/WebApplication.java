package com.payconiq.stocks.webapp;

import com.payconiq.stocks.webapp.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, ApplicationProperties applicationProperties) {
        return restTemplateBuilder
                .rootUri(applicationProperties.getApiUrl())
                .build();
    }
}
