package com.amycardoso.bookmarket.repository

import com.amycardoso.bookmarket.model.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : JpaRepository<Customer, Int> {
    fun findByNameContaining(name: String, pageable: Pageable): Page<Customer>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): Customer?
}