package com.amycardoso.bookmarket.extension

import com.amycardoso.bookmarket.controller.request.PostBookRequest
import com.amycardoso.bookmarket.controller.request.PostCustomerRequest
import com.amycardoso.bookmarket.controller.request.PutCustomerRequest
import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.model.Book
import com.amycardoso.bookmarket.model.Customer

fun PostCustomerRequest.toCustomerModel(): Customer {
    return Customer(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Int): Customer {
    return Customer(id = id, name = this.name, email = this.email)
}

fun PostBookRequest.toBookModel(customer: Customer): Book {
    return Book(
        name = this.name,
        price = this.price,
        status = BookStatus.ACTIVE,
        customer = customer
    )
}