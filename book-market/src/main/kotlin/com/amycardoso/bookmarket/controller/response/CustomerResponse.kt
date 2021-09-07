package com.amycardoso.bookmarket.controller.response

import com.amycardoso.bookmarket.enums.CustomerStatus

data class CustomerResponse(
    var id: Int? = null,
    var name: String,
    var email: String,
    var status: CustomerStatus
)