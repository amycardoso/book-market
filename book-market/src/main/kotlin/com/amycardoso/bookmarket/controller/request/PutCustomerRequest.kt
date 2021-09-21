package com.amycardoso.bookmarket.controller.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCustomerRequest (
    @field:NotEmpty(message = "Name must be informed")
    var name: String,
    @field:Email(message = "Email must be valid")
    var email: String
)