package com.example.funwithspringretry

import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class RetryableWorking {
    val count = AtomicInteger(0)

    @Retryable(value = [RuntimeException::class])
    fun foo() {
        count.incrementAndGet()
        throw RuntimeException()
    }
}