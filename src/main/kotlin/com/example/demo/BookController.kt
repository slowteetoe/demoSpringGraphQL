package com.example.demo

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated

@Validated
@Controller
class BookController {

    @QueryMapping
    fun bookById(@Argument id: String): Book? {
        return Book.getById(id)
    }

    @SchemaMapping
    fun author(book: Book) : Author? {
        return book.author
    }

    @MutationMapping
    fun addBook(@Argument book: BookInput) : Book {
        var author = Author.findByName(firstName=book.author.firstName, lastName=book.author.lastName)
        if (author == null) {
            author = Author.create(firstName=book.author.firstName, lastName=book.author.lastName)
            println("Created $author")
        }
        println("Creating $book")
        return Book.create(book, author)
    }
}

// TODO schemagen
data class BookInput(val title: String, val author: AuthorInput, val pageCount: Int)
data class AuthorInput(val firstName: String, val lastName: String)