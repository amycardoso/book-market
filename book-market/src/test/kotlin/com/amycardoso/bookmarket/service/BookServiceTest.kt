package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.exception.NotFoundException
import com.amycardoso.bookmarket.helper.buildBook
import com.amycardoso.bookmarket.model.Book
import com.amycardoso.bookmarket.repository.BookRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*

@ExtendWith(MockKExtension::class)
class BookServiceTest {
    @MockK
    private lateinit var bookRepository: BookRepository

    @InjectMockKs
    @SpyK
    private lateinit var bookService: BookService

    @Test
    fun `should create book`() {
        val fakeBook = buildBook()

        every { bookRepository.save(fakeBook) } returns fakeBook

        bookService.create(fakeBook)

        verify(exactly = 1) { bookRepository.save(fakeBook) }
    }

    @Test
    fun `should return all books`() {
        val fakeBooks = listOf(buildBook(), buildBook())
        val pageable = Pageable.ofSize(fakeBooks.size)
        val pageableBooks = PageImpl<Book>(fakeBooks, pageable, fakeBooks.size.toLong())

        every { bookRepository.findAll(pageable) } returns pageableBooks

        val books = bookService.findAll(pageable)

        Assertions.assertEquals(pageableBooks, books)
        verify(exactly = 1) { bookRepository.findAll(pageable) }
    }

    @Test
    fun `should return all active books`() {
        val activeStatus = BookStatus.ACTIVE
        val fakeBooks = listOf(buildBook(status = activeStatus), buildBook(status = activeStatus))
        val pageable = Pageable.ofSize(fakeBooks.size)
        val pageableBooks = PageImpl<Book>(fakeBooks, pageable, fakeBooks.size.toLong())

        every { bookRepository.findByStatus(activeStatus, pageable) } returns pageableBooks

        val books = bookService.findActives(pageable)

        Assertions.assertEquals(pageableBooks, books)
        verify(exactly = 1) { bookRepository.findByStatus(activeStatus, pageable) }
    }

    @Test
    fun `should return book by id`() {
        val id = Random().nextInt()
        val fakeBook = buildBook(id = id)

        every { bookRepository.findById(id) } returns Optional.of(fakeBook)

        val book = bookService.findById(id)

        Assertions.assertEquals(fakeBook, book)
        verify(exactly = 1) { bookRepository.findById(id) }
    }

    @Test
    fun `should throw not found when find by id `() {
        val id = Random().nextInt()

        every { bookRepository.findById(id) } returns Optional.empty()

        val error = assertThrows<NotFoundException>{
            bookService.findById(id)
        }

        Assertions.assertEquals("Book [${id}] not exists", error.message)
        verify(exactly = 1) { bookRepository.findById(id) }
    }

}