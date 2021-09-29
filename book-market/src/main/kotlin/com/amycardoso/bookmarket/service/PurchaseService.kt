package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.model.Purchase
import com.amycardoso.bookmarket.repository.PurchaseRepository
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository
) {

    fun create(purchase: Purchase) {
        purchaseRepository.save(purchase)
    }

}
