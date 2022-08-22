# Fun With Spring Retry

Spring's @Retryable annotation shows some unexpected behavior when a @Retryable method is invoked from within the same class. In this case the retries will not be executed as expected.

Consider the following **working** example:

```kotlin
@Component
class RetryableWorking {
    val count = AtomicInteger(0)

    @Retryable(value = [RuntimeException::class])
    fun foo() {
        count.incrementAndGet()
        throw RuntimeException()
    }
}

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
```

In this case the @Retryable method `foo` is executed three times as expected given the default configuration of spring-retry.

Now consider the following example, which **does not work**:

```kotlin
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
```

In this case the @Retryable method `foo` is executed only once (i.e. `count == 1`). This is due to it being called from `bar`, which is located in the same class as `foo`.