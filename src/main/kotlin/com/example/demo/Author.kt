package com.example.demo

import java.util.concurrent.atomic.AtomicInteger

data class Author(val id: String, val firstName: String, val lastName: String) {

    companion object {

        val nextId = AtomicInteger(4)

        private val authors: MutableList<Author> = mutableListOf(
            Author("author-1", "Joshua", "Bloch"),
            Author("author-2", "Douglas", "Adams"),
            Author("author-3", "Bill", "Bryson")
        )

        fun getById(id: String): Author? {
            return authors.firstOrNull { it.id.equals(id, ignoreCase = true) }
        }

        fun findByName(firstName: String, lastName: String) : Author? {
            return authors.firstOrNull {
                it.firstName.equals(firstName, ignoreCase = true)
                        && it.lastName.equals(lastName, ignoreCase = true) }
        }

        fun create(firstName: String, lastName: String) : Author{
            val author = findByName(firstName,lastName)
            return if (author != null ){
                author
            } else {
                val newAuthor = Author(id= "author-${nextId.getAndIncrement()}",
                    firstName = firstName,
                    lastName = lastName,
                )
                authors.add(newAuthor)
                newAuthor
            }
        }
    }
}
