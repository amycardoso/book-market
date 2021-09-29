package com.amycardoso.bookmarket.controller.mapper

import com.amycardoso.bookmarket.controller.request.PostPurchaseRequest
import com.amycardoso.bookmarket.model.Purchase
import com.amycardoso.bookmarket.service.BookService
import com.amycardoso.bookmarket.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    fun toModel(request: PostPurchaseRequest): Purchase {
        val customer = customerService.findById(request.customerId)
        val books = bookService.findAllByIds(request.bookIds)

        return Purchase(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )
    }

}