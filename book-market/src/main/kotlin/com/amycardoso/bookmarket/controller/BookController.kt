package com.amycardoso.bookmarket.controller

import com.amycardoso.bookmarket.controller.request.PostBookRequest
import com.amycardoso.bookmarket.extension.toBookModel
import com.amycardoso.bookmarket.service.BookService
import com.amycardoso.bookmarket.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("book")
class BookController (
    val bookService: BookService,
    val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PostBookRequest) {
        val customer = customerService.findById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

}