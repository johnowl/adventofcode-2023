package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NumberPositionTest {
    @Test
    fun `should return true if NumberPosition contains x and y in the start`() {
        val numberPosition = NumberPosition(1, 2, 3)

        val result = numberPosition.contains(1, 2)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true if NumberPosition contains x and y in the middle`() {
        val numberPosition = NumberPosition(1, 2, 3)

        val result = numberPosition.contains(2, 2)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true if NumberPosition contains x and y in the end`() {
        val numberPosition = NumberPosition(1, 2, 3)

        val result = numberPosition.contains(3, 2)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return false if NumberPosition does not contain x and y`() {
        val numberPosition = NumberPosition(1, 2, 3)

        val result = numberPosition.contains(0, 2)

        assertThat(result).isFalse()
    }
}