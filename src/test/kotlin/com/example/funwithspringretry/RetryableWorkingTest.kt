package com.example.funwithspringretry

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RetryableWorkingTest {
    @Autowired
    lateinit var cut: RetryableWorking

    @Test
    fun testRetryableWorking() {
        assertThrows<RuntimeException> {
            cut.foo()
        }
        assertEquals(3, cut.count.get())
    }
}