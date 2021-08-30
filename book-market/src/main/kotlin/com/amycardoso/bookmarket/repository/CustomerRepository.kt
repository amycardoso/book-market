package com.amycardoso.bookmarket.repository

import com.amycardoso.bookmarket.model.Customer
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Int> {
    fun findByNameContaining(name: String): List<Customer>
}