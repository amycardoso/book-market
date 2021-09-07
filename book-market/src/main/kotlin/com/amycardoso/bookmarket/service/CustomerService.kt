package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.model.Customer
import com.amycardoso.bookmarket.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService (
    val customerRepository : CustomerRepository,
    val bookService: BookService
){

    fun create(customer: Customer) {
        customerRepository.save(customer)
    }

    fun update(customer: Customer) {
        if(!customerRepository.existsById(customer.id!!)){
            throw Exception()
        }
        customerRepository.save(customer)
    }

    fun findAll(name: String?): List<Customer> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }
        return customerRepository.findAll().toList()
    }

    fun findById(id: Int): Customer {
        return customerRepository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)
        customerRepository.deleteById(id)
    }

}