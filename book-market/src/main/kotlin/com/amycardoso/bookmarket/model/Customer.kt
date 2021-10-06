package com.amycardoso.bookmarket.model

import com.amycardoso.bookmarket.enums.CustomerStatus
import com.amycardoso.bookmarket.enums.Profile
import javax.persistence.*

@Entity(name = "customer")
data class Customer (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,
    @Column
    var name: String,
    @Column
    var email: String,
    @Column
    @Enumerated(EnumType.STRING)
    var status: CustomerStatus,
    @Column
    val password: String,
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
    var roles: Set<Profile> = setOf()
)