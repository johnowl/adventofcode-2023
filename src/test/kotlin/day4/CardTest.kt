package day4

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CardTest {
    @Test
    fun `should calculate points correctly`() {
        val card = Card(
            "Card 1",
            setOf(41, 48, 83, 86, 17),
            setOf(83, 86, 6, 31, 17, 9, 48, 53)
        )

        val result = card.calculatePoints()

        assertThat(result).isEqualTo(8)
    }
}