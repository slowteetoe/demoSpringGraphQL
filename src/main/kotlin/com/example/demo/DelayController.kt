package com.example.demo

import kotlinx.coroutines.delay
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated

@Validated
@Controller
class DelayController {

    @MutationMapping
    suspend fun slowMutation(@Argument input: SlowMutationInput): SlowMutationResult {
        input.delayInMs?.run {
            delay(timeMillis = this.toLong())
        }
        if (input.shouldError == true) {
            throw RuntimeException("intentional failure")
        } else {
            return SlowMutationResult("Ok")
        }
    }

}

data class SlowMutationInput(val delayInMs: Int?, val shouldError: Boolean?)
data class SlowMutationResult(val result: String)