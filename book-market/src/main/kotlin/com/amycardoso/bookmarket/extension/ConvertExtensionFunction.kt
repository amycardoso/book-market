package com.amycardoso.bookmarket.extension

import com.amycardoso.bookmarket.controller.request.PostBookRequest
import com.amycardoso.bookmarket.controller.request.PostCustomerRequest
import com.amycardoso.bookmarket.controller.request.PutBookRequest
import com.amycardoso.bookmarket.controller.request.PutCustomerRequest
import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.enums.CustomerStatus
import com.amycardoso.bookmarket.model.Book
import com.amycardoso.bookmarket.model.Customer

fun PostCustomerRequest.toCustomerModel(): Customer {
    return Customer(name = this.name, email = this.email, status = CustomerStatus.ACTIVE)
}

fun PutCustomerRequest.toCustomerModel(previousValue: Customer): Customer {
    return Customer(id = previousValue.id, name = this.name, email = this.email, status = previousValue.status)
}

fun PostBookRequest.toBookModel(customer: Customer): Book {
    return Book(
        name = this.name,
        price = this.price,
        status = BookStatus.ACTIVE,
        customer = customer
    )
}

fun PutBookRequest.toBookModel(previousValue: Book): Book {
    return Book(
        id = previousValue.id,
        name = this.name ?: previousValue.name,
        price = this.price ?: previousValue.price,
        status = previousValue.status,
        customer = previousValue.customer
    )
}