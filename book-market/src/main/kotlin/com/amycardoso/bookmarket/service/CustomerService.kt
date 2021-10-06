package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.enums.CustomerStatus
import com.amycardoso.bookmarket.enums.Profile
import com.amycardoso.bookmarket.exception.NotFoundException
import com.amycardoso.bookmarket.model.Customer
import com.amycardoso.bookmarket.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService (
    private val customerRepository : CustomerRepository,
    private val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
){

    fun create(customer: Customer) {
        val customerCopy = customer.copy(
            roles = setOf(Profile.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        customerRepository.save(customerCopy)
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
        return customerRepository.findById(id).orElseThrow{ NotFoundException("Customer not exists") }
    }

    fun delete(id: Int) {
        val customer = findById(id)
        bookService.deleteByCustomer(customer)
        customer.status = CustomerStatus.INACTIVE
        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

}