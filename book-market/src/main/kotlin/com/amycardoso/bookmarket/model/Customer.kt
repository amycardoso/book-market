package com.amycardoso.bookmarket.model

import com.amycardoso.bookmarket.enums.CustomerStatus
import javax.persistence.*

@Entity(name = "customer")
class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column
    var name: String,
    @Column
    var email: String,
    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus
)