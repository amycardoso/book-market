package com.amycardoso.bookmarket.repository

import com.amycardoso.bookmarket.model.Purchase
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository : CrudRepository<Purchase, Int> {

}