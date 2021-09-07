package com.amycardoso.bookmarket.controller.response

import com.amycardoso.bookmarket.enums.BookStatus
import com.amycardoso.bookmarket.model.Customer
import java.math.BigDecimal

data class BookResponse(
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    var customer: Customer? = null,
    var status: BookStatus? = null
)