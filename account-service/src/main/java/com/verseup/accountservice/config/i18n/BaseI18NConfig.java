package com.verseup.accountservice.config.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class BaseI18NConfig {

    @Bean
    public LocalMessageResolver localMessageResolver() {
        return new LocalMessageResolver();
    }
}
