package com.example.training.service.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Transient
import org.springframework.security.core.authority.AuthorityUtils

@Transient
class ApiKeyAuthenticationToken(private var apiKey: String?= null, authenticated: Boolean = true) : AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {

    init {
        super.setAuthenticated(authenticated)
    }

    override fun getCredentials(): Any? {
        return null
    }

    override fun getPrincipal(): Any? {
        return apiKey
    }
}