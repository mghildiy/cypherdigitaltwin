package com.cypherlabs.cypherdigitaltwin.config;

import de.huxhorn.sulky.ulid.ULID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ULIDConfig {

    @Bean
    public ULID ulidGenerator() {
        return new ULID();
    }
}
