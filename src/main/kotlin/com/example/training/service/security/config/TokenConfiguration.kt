package com.example.training.service.security.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("api-key")
class TokenConfiguration {
    lateinit var token: String
}