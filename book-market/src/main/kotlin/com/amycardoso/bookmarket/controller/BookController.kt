package com.amycardoso.bookmarket.controller

import com.amycardoso.bookmarket.controller.request.PostBookRequest
import com.amycardoso.bookmarket.controller.request.PutBookRequest
import com.amycardoso.bookmarket.extension.toBookModel
import com.amycardoso.bookmarket.model.Book
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

    @GetMapping
    fun findAll(): List<Book> {
        return bookService.findAll()
    }

    @GetMapping("/active")
    fun findActives(): List<Book> =
        bookService.findActives()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): Book {
        return bookService.findById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody book: PutBookRequest) {
        val savedBook = bookService.findById(id)
        bookService.update(book.toBookModel(savedBook))
    }

}