package com.amycardoso.bookmarket.controller

import com.amycardoso.bookmarket.controller.request.PostBookRequest
import com.amycardoso.bookmarket.controller.request.PutBookRequest
import com.amycardoso.bookmarket.controller.response.BookResponse
import com.amycardoso.bookmarket.controller.response.PageResponse
import com.amycardoso.bookmarket.extension.toBookModel
import com.amycardoso.bookmarket.extension.toPageResponse
import com.amycardoso.bookmarket.extension.toResponse
import com.amycardoso.bookmarket.service.BookService
import com.amycardoso.bookmarket.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("books")
class BookController (
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: PostBookRequest) {
        val customer = customerService.findById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): ResponseEntity<PageResponse<BookResponse>> {
        return ResponseEntity.ok(bookService.findAll(pageable).map { it.toResponse() }.toPageResponse())
    }

    @GetMapping("/active")
    fun findActives(
        @PageableDefault(
            page = 0,
            size = 10
        ) pageable: Pageable
    ): ResponseEntity<PageResponse<BookResponse>> {
        return ResponseEntity.ok(bookService.findActives(pageable).map { it.toResponse() }.toPageResponse())
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<BookResponse> {
        return ResponseEntity.ok(bookService.findById(id).toResponse())
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