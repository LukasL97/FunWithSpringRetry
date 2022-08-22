package com.example.funwithspringretry

import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class RetryableNotWorking {
    val count = AtomicInteger(0)

    @Retryable(value = [RuntimeException::class])
    fun foo() {
        count.incrementAndGet()
        throw RuntimeException()
    }

    fun bar() {
        foo()
    }
}