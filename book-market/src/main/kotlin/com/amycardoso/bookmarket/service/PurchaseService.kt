package com.amycardoso.bookmarket.service

import com.amycardoso.bookmarket.events.PurchaseEvent
import com.amycardoso.bookmarket.model.Purchase
import com.amycardoso.bookmarket.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun create(purchase: Purchase) {
        purchaseRepository.save(purchase)
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchase))
    }

    fun update(purchaseModel: Purchase) {
        purchaseRepository.save(purchaseModel)
    }

}
