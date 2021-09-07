package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.model.Book
import com.amycardoso.bookmarket.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun create(book: Book) {
        bookRepository.save(book)
    }

}