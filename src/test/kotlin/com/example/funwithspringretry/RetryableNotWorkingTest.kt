package com.example.funwithspringretry

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RetryableNotWorkingTest {
    @Autowired
    lateinit var cut: RetryableNotWorking

    @Test
    fun testRetryableWorking() {
        org.junit.jupiter.api.assertThrows<RuntimeException> {
            cut.bar()
        }
        assertEquals(3, cut.count.get())
    }
}