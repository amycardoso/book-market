package com.amycardoso.bookmarket.controller.response

data class ErrorResponse(
    var httpCode: Int,
    var message: String,
    var errors: List<FieldErrorResponse>?
)