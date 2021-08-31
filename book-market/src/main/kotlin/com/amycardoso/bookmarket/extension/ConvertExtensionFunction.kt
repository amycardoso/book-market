package com.amycardoso.bookmarket.extension

import com.amycardoso.bookmarket.controller.request.PostCustomerRequest
import com.amycardoso.bookmarket.controller.request.PutCustomerRequest
import com.amycardoso.bookmarket.model.Customer

fun PostCustomerRequest.toCustomerModel(): Customer {
    return Customer(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Int): Customer {
    return Customer(id = id, name = this.name, email = this.email)
}