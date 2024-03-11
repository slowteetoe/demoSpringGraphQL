package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest
import org.springframework.graphql.test.tester.GraphQlTester

@GraphQlTest(BookController::class)
class BookControllerTests {
    @Autowired
    lateinit var graphQlTester: GraphQlTester

    @Test
    fun `should get second book`(){
        this.graphQlTester
            .documentName("bookDetails")
            .variable("id", "book-2")
            .execute()
            .path("bookById")
            .matchesJsonStrictly("""
                {"id":"book-2","title":"Hitchhiker's Guide to the Galaxy","pageCount":208,"author":{"id":"author-2","firstName":"Douglas","lastName":"Adams"}}
            """.trimIndent())
    }

    @Test
    fun `book I don't have`(){
        this.graphQlTester
            .documentName("bookDetails")
            .variable("id", "book-500")
            .execute()
            .path("bookById")
            .valueIsNull()
    }

    @Test
    fun `should add a new unique book`(){
        this.graphQlTester
            .documentName("addBook")
            .operationName("addBook")
            .variable("book", mapOf(
                "pageCount" to 1234,
                "title" to "Something Great",
                "author" to mapOf("firstName" to "Ann", "lastName" to "Onymous"),
            ))
            .execute()
            .path("addBook")
            // this only works while there's 1 test inserting data, otherwise these IDs would be non-deterministic
            .matchesJsonStrictly("""
                {"id":"book-4","title":"Something Great","pageCount":1234,"author":{"id":"author-4","firstName":"Ann","lastName":"Onymous"}}
                """.trimIndent())
    }
}