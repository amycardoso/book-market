package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.enums.BookStatus
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

    fun findAll(): List<Book> {
        return bookRepository.findAll().toList()
    }

    fun findActives(): List<Book> {
        return bookRepository.findByStatus(BookStatus.ACTIVE)
    }

    fun findById(id: Int): Book {
        return bookRepository.findById(id).orElseThrow()
    }

    fun delete(id: Int) {
        val book = findById(id)
        book.status = BookStatus.CANCELED
        update(book)
    }

    fun update(book: Book) {
        bookRepository.save(book)
    }
}