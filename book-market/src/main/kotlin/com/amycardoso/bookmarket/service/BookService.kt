package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.exception.NotFoundException
import com.amycardoso.bookmarket.model.Book
import com.amycardoso.bookmarket.model.Customer
import com.amycardoso.bookmarket.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun create(book: Book) {
        bookRepository.save(book)
    }

    fun findAll(pageable: Pageable): Page<Book> {
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<Book> {
        return bookRepository.findByStatus(BookStatus.ACTIVE, pageable)
    }

    fun findById(id: Int): Book {
        return bookRepository.findById(id).orElseThrow{ NotFoundException("Book not exists") }
    }

    fun delete(id: Int) {
        val book = findById(id)
        book.status = BookStatus.CANCELED
        update(book)
    }

    fun update(book: Book) {
        bookRepository.save(book)
    }

    fun deleteByCustomer(customer: Customer) {
        val books = bookRepository.findByCustomer(customer)
        for(book in books) {
            book.status = BookStatus.DELETED
        }
        bookRepository.saveAll(books)
    }

    fun findAllByIds(bookIds: Set<Int>): List<Book> {
        return bookRepository.findAllById(bookIds).toList()
    }

    fun purchase(books: MutableList<Book>) {
        books.map {
            it.status = BookStatus.SOLD
        }
        bookRepository.saveAll(books)
    }
}