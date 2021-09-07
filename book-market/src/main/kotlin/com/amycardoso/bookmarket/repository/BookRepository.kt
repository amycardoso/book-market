package com.amycardoso.bookmarket.repository

import com.amycardoso.bookmarket.model.Book
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<Book, Int> {

}