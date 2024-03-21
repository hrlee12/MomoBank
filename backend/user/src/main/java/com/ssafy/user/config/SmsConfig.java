package com.ssafy.user.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    @Bean
    DefaultMessageService defaultMessageService( @Value("${sms.api-key}") String apiKey,
                                     @Value("${sms.api-secret-key}") String apiSecretKey,
                                     @Value("${sms.url}") String url){

        return NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, url);
    }
}
