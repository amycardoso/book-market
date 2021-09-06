package com.amycardoso.bookmarket.model

import com.amycardoso.bookmarket.enums.BookStatus
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
class Book (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null,
    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
)