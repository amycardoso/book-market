package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.exception.AuthenticationException
import com.amycardoso.bookmarket.repository.CustomerRepository
import com.amycardoso.bookmarket.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository
): UserDetailsService {

    override fun loadUserByUsername(id: String): UserDetails {
        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException("User not found") }
        return UserCustomDetails(customer)
    }

}