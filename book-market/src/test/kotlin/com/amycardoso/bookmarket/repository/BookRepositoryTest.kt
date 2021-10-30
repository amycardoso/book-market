package com.amycardoso.bookmarket.repository

import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.helper.buildBook
import com.amycardoso.bookmarket.helper.buildCustomer
import com.amycardoso.bookmarket.model.Book
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
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

        assertThat(pageableBooks).usingRecursiveFieldByFieldElementComparatorIgnoringFields("price")
            .isEqualTo(books);
    }

    @Test
    fun `should return book by customer`() {
        val customer = customerRepository.save(buildCustomer())
        val anotherCustomer = customerRepository.save(buildCustomer())

        val book1 = bookRepository.save(buildBook(name = "book 1", customer = customer))
        val book2 = bookRepository.save(buildBook(name = "book 2", customer = customer))
        bookRepository.save(buildBook(name = "book 3", customer = anotherCustomer))

        val fakeBooks = listOf(book1, book2);
        val books = bookRepository.findByCustomer(customer);

        assertThat(fakeBooks).usingRecursiveFieldByFieldElementComparatorIgnoringFields("price")
            .isEqualTo(books);
    }

}