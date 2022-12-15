package com.example.training.service.security

import com.example.training.service.security.config.TokenConfiguration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component


@Component
class ApiKeyAuthenticationProvider(private val tokenConfiguration: TokenConfiguration) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val apiKey = authentication.principal as String?
        if (apiKey.isNullOrEmpty()) {
            throw InsufficientAuthenticationException("No API key in request")
        } else {
            if (apiKey == tokenConfiguration.token) {
                return ApiKeyAuthenticationToken(apiKey, true)
            }
            throw BadCredentialsException("API Key is invalid")
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return ApiKeyAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}