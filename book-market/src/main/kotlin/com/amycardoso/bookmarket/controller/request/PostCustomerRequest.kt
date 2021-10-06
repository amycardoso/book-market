package com.amycardoso.bookmarket.controller.request

import com.amycardoso.bookmarket.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest (
    @field:NotEmpty(message = "Name must be informed")
    var name: String,
    @field:Email(message = "Email must be valid")
    @EmailAvailable(message = "Email in use")
    var email: String,
    @field:NotEmpty(message = "Password must be informed")
    var password: String
)