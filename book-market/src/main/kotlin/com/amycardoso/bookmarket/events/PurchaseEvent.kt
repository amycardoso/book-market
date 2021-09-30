package com.amycardoso.bookmarket.events

import com.amycardoso.bookmarket.model.Purchase
import org.springframework.context.ApplicationEvent

class PurchaseEvent (
    source: Any,
    val purchaseModel: Purchase
): ApplicationEvent(source)