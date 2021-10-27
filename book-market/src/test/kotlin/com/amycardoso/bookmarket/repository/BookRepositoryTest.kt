package com.amycardoso.bookmarket.repository

import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.helper.buildBook
import com.amycardoso.bookmarket.helper.buildCustomer
import com.amycardoso.bookmarket.model.Book
import com.amycardoso.bookmarket.model.Customer
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class BookRepositoryTest {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() = bookRepository.deleteAll()

    @Test
    fun `should return book by deleted status`() {
        val customer = customerRepository.save(buildCustomer())

        val deletedStatus = BookStatus.DELETED;
        val deletedBook1 = bookRepository.save(buildBook(name = "book 1", status = deletedStatus, customer = customer))
        val deletedBook2 = bookRepository.save(buildBook(name = "book 2", status = deletedStatus, customer = customer))
        bookRepository.save(buildBook(name = "book 3"))

        val fakeBooks = listOf(deletedBook1, deletedBook2);
        val pageable = Pageable.unpaged();
        val pageableBooks = PageImpl<Book>(fakeBooks, pageable, fakeBooks.size.toLong())

        val books = bookRepository.findByStatus(deletedStatus, pageable);
        Assertions.assertEquals(pageableBooks, books)
    }

}