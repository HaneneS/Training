package com.example.training.service.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class ApiKeyAuthenticationFilter(authenticationManager: AuthenticationManager?) :
    AbstractAuthenticationProcessingFilter("/**") {
    init {
        this.authenticationManager = authenticationManager
    }

    override fun attemptAuthentication(
        request: HttpServletRequest, response: HttpServletResponse
    ): Authentication {
        val token: ApiKeyAuthenticationToken =
            request.getHeader("x-api-key")?.let { ApiKeyAuthenticationToken(it, true) } ?: (ApiKeyAuthenticationToken())
        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }
}