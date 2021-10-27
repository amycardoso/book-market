package com.amycardoso.bookmarket.helper

import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.enums.CustomerStatus
import com.amycardoso.bookmarket.enums.Role
import com.amycardoso.bookmarket.model.Book
import com.amycardoso.bookmarket.model.Customer
import com.amycardoso.bookmarket.model.Purchase
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

fun buildCustomer(
    id: Int? = null,
    name: String = "customer name",
    email: String = "${UUID.randomUUID()}@email.com",
    password: String = "password"
) = Customer (
    id = id,
    name = name,
    email = email,
    status = CustomerStatus.ACTIVE,
    password = password,
    roles = setOf(Role.CUSTOMER)
)

fun buildPurchase(
    id: Int? = null,
    customer: Customer = buildCustomer(),
    books: MutableList<Book> = mutableListOf(),
    nfe: String = UUID.randomUUID().toString(),
    price: BigDecimal = BigDecimal.TEN
) = Purchase (
    id = id,
    customer = customer,
    books = books,
    nfe = nfe,
    price = price
)

fun buildBook(
    id: Int? = null,
    name: String = "book name",
    price: BigDecimal = BigDecimal(0).setScale(2, RoundingMode.HALF_UP).stripTrailingZeros(),
    customer: Customer? = null,
    status: BookStatus = BookStatus.ACTIVE
) = Book (
    id = id,
    name = name,
    price = price,
    customer = customer,
    status = status
)