package com.amycardoso.bookmarket.controller

import com.amycardoso.bookmarket.controller.request.PostBookRequest
import com.amycardoso.bookmarket.controller.request.PutBookRequest
import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.helper.buildBook
import com.amycardoso.bookmarket.helper.buildCustomer
import com.amycardoso.bookmarket.repository.BookRepository
import com.amycardoso.bookmarket.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.collection.IsCollectionWithSize
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.math.RoundingMode

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class BookControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        bookRepository.deleteAll()
    }

    @Test
    fun `should return all books`() {
        val customer = customerRepository.save(buildCustomer())
        val book1 = bookRepository.save(buildBook(customer = customer))
        val book2 = bookRepository.save(buildBook(customer = customer))

        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].id").value(book1.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].name").value(book1.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].price").value(book1.price))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].customer.id").value(book1.customer?.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].status").value(book1.status?.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].id").value(book2.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].name").value(book2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].price").value(book2.price))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].customer.id").value(book2.customer?.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].status").value(book2.status?.name))
    }

    @Test
    fun `should return all active books`() {
        val customer = customerRepository.save(buildCustomer())
        val book1 = bookRepository.save(buildBook(customer = customer))
        val book2 = bookRepository.save(buildBook(customer = customer))
        bookRepository.save(buildBook(customer = customer, status = BookStatus.CANCELED))

        mockMvc.perform(MockMvcRequestBuilders.get("/books/active"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.items", IsCollectionWithSize.hasSize<Array<Any>>(2)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].id").value(book1.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].name").value(book1.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].price").value(book1.price))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].customer.id").value(book1.customer?.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[0].status").value(book1.status?.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].id").value(book2.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].name").value(book2.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].price").value(book2.price))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].customer.id").value(book2.customer?.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.items.[1].status").value(book2.status?.name))
    }

    @Test
    fun `should create book`() {
        val customer = customerRepository.save(buildCustomer())

        val request = PostBookRequest(
            "Book", BigDecimal(Math.random()).setScale(2, RoundingMode.HALF_UP),
            customer.id!!
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isCreated)

        val books = bookRepository.findAll().toList()
        Assertions.assertEquals(1, books.size)
        Assertions.assertEquals(request.name, books[0].name)
        Assertions.assertEquals(request.price, books[0].price)
        Assertions.assertEquals(request.customerId, books[0].customer!!.id)
    }

    @Test
    fun `should get book by id`() {
        val customer = customerRepository.save(buildCustomer())
        val book = bookRepository.save(buildBook(customer = customer))

        mockMvc.perform(MockMvcRequestBuilders.get("/books/${book.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(book.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(book.name))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(book.price))
            .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").value(book.customer?.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(book.status?.name))
    }

    @Test
    fun `should delete book`() {
        val customer = customerRepository.save(buildCustomer())
        val book = bookRepository.save(buildBook(customer = customer))

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/${book.id}"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        val bookDeleted = bookRepository.findById(book.id!!)
        Assertions.assertEquals(BookStatus.CANCELED, bookDeleted.get().status)
    }

    @Test
    fun `should return not found when a book to be deleted not exists`() {
        mockMvc.perform(MockMvcRequestBuilders.delete("/books/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode").value(404))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book [1] not exists"))
    }

    @Test
    fun `should update book`() {
        val customer = customerRepository.save(buildCustomer())
        val book = bookRepository.save(buildBook(customer = customer))
        val request = PutBookRequest("Book name update", BigDecimal(Math.random()).setScale(2, RoundingMode.HALF_UP))

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/${book.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isNoContent)

        val books = bookRepository.findAll().toList()
        Assertions.assertEquals(1, books.size)
        Assertions.assertEquals(request.name, books[0].name)
        Assertions.assertEquals(request.price, books[0].price)
    }

    @Test
    fun `should return not found when update not existing book`() {
        val request = PutBookRequest("book", BigDecimal(Math.random()).setScale(2, RoundingMode.HALF_UP))

        mockMvc.perform(
            MockMvcRequestBuilders.put("/books/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode").value(404))
            .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Book [1] not exists"))
    }

}