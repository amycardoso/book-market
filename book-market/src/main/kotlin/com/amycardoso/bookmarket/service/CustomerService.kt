package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.model.Customer
import com.amycardoso.bookmarket.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService (
    val repository : CustomerRepository
){

    fun create(customer: Customer) {
        repository.save(customer)
    }

    fun update(customer: Customer) {
        if(!repository.existsById(customer.id!!)){
            throw Exception()
        }
        repository.save(customer)
    }

    fun findAll(name: String?): List<Customer> {
        name?.let {
            return repository.findByNameContaining(it)
        }
        return repository.findAll().toList()
    }

    fun findById(id: Int): Customer {
        return repository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        if(!repository.existsById(id)){
            throw Exception()
        }
        repository.deleteById(id)
    }

}