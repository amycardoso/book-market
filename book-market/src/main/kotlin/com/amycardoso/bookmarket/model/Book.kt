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
    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null
) {

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if(field == BookStatus.CANCELED || field == BookStatus.DELETED)
                throw Exception("It's not possible to update a book with status $field")
            field = value
        }

    constructor(id: Int? = null,
                name: String,
                price: BigDecimal,
                customer: Customer? = null,
                status: BookStatus?): this(id, name, price, customer) {
        this.status = status
    }

}