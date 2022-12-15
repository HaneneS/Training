package com.example.training.service.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(private val apiKeyAuthenticationProvider: ApiKeyAuthenticationProvider) : WebSecurityConfigurerAdapter() {

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(
                ApiKeyAuthenticationFilter(authenticationManager()),
                AnonymousAuthenticationFilter::class.java
            )
            .csrf().disable()
    }

    @Bean
    public override fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf(apiKeyAuthenticationProvider))
    }
}