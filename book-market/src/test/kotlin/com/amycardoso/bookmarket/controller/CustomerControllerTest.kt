package com.amycardoso.bookmarket.controller

import com.amycardoso.bookmarket.helper.buildCustomer
import com.amycardoso.bookmarket.repository.CustomerRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@ActiveProfiles("test")
@WithMockUser
class CustomerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun `should return all customers`() {
        val customer1 = customerRepository.save(buildCustomer())
        val customer2 = customerRepository.save(buildCustomer())

        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.items.[0].id").value(customer1.id))
            .andExpect(jsonPath("$.items.[0].name").value(customer1.name))
            .andExpect(jsonPath("$.items.[0].email").value(customer1.email))
            .andExpect(jsonPath("$.items.[0].status").value(customer1.status.name))
            .andExpect(jsonPath("$.items.[1].id").value(customer2.id))
            .andExpect(jsonPath("$.items.[1].name").value(customer2.name))
            .andExpect(jsonPath("$.items.[1].email").value(customer2.email))
            .andExpect(jsonPath("$.items.[1].status").value(customer2.status.name))
    }

    @Test
    fun `should filter all customers by name when get all`() {
        val customer1 = customerRepository.save(buildCustomer(name = "Bruno"))
        customerRepository.save(buildCustomer(name = "Giuliano"))

        mockMvc.perform(get("/customers?name=Bru"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.totalItems").value(1))
            .andExpect(jsonPath("$.items.[0].id").value(customer1.id))
            .andExpect(jsonPath("$.items.[0].name").value(customer1.name))
            .andExpect(jsonPath("$.items.[0].email").value(customer1.email))
            .andExpect(jsonPath("$.items.[0].status").value(customer1.status.name))
    }

}