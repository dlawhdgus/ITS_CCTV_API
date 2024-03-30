package com.example.krtr.config;

import com.example.krtr.util.getCCTVUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class appConfig {

    @Bean
    public getCCTVUrl getCCTVUrlBean() {
        return new getCCTVUrl();
    }
}
