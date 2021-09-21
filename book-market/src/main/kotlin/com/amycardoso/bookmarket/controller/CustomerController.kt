package com.amycardoso.bookmarket.controller

import com.amycardoso.bookmarket.controller.request.PostCustomerRequest
import com.amycardoso.bookmarket.controller.request.PutCustomerRequest
import com.amycardoso.bookmarket.controller.response.CustomerResponse
import com.amycardoso.bookmarket.extension.toCustomerModel
import com.amycardoso.bookmarket.extension.toResponse
import com.amycardoso.bookmarket.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController (
    val customerService: CustomerService
){
    @GetMapping
    fun getAll(@RequestParam name: String?, @PageableDefault(page = 0, size = 10) pageable: Pageable): ResponseEntity<Page<CustomerResponse>> {
        return ResponseEntity.ok(customerService.findAll(name, pageable).map { it.toResponse() })
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid customer: PostCustomerRequest) {
        customerService.create(customer.toCustomerModel())
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Int): ResponseEntity<CustomerResponse> {
        return ResponseEntity.ok(customerService.findById(id).toResponse())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody @Valid customer: PutCustomerRequest) {
        val savedCustomer = customerService.findById(id)
        customerService.update(customer.toCustomerModel(savedCustomer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customerService.delete(id)
    }
}