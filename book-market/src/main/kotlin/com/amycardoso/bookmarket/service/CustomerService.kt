package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.enums.CustomerStatus
import com.amycardoso.bookmarket.model.Customer
import com.amycardoso.bookmarket.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    fun findAll(name: String?, pageable: Pageable): Page<Customer> {
        name?.let {
            return customerRepository.findByNameContaining(it, pageable)
        }
        return customerRepository.findAll(pageable)
    }

    fun findById(id: Int): Customer {
        return customerRepository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)
        customer.status = CustomerStatus.INACTIVE
        customerRepository.save(customer)
    }

}