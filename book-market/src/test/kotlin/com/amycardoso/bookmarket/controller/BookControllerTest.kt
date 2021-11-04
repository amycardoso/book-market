package com.amycardoso.bookmarket.controller

import com.amycardoso.bookmarket.helper.buildBook
import com.amycardoso.bookmarket.helper.buildCustomer
import com.amycardoso.bookmarket.repository.BookRepository
import com.amycardoso.bookmarket.repository.CustomerRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

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
}