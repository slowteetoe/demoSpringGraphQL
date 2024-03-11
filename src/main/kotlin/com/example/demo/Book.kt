package com.example.demo

import java.util.concurrent.atomic.AtomicInteger

data class Book(val id: String, val title: String, val pageCount: Int, val author: Author) {

    companion object {

        val nextId = AtomicInteger(4)

        private val books = mutableListOf(
            Book("book-1", "Effective Java", 416, Author.getById("author-1")!!),
            Book("book-2", "Hitchhiker's Guide to the Galaxy", 208, Author.getById("author-2")!!),
            Book("book-3", "Down Under", 436, Author.getById("author-3")!!)
        )

        fun getById(id: String): Book? {
            return books.firstOrNull { it.id == id }
        }

        fun create(bookInput: BookInput, author: Author) : Book {
            books.find { it.title == bookInput.title }?.let { return it }

            val newBook = Book(
                id = "book-${nextId.getAndIncrement()}",
                title = bookInput.title,
                pageCount = bookInput.pageCount,
                author = author,
            )
            books.add(newBook)
            return newBook
        }
    }
}