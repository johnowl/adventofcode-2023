package day6

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RaceTest {
    @Test
    fun `given button pressed for 3 milliseconds the boat should travel 12 millimeters`() {
        val race = Race(7, 9)
        val result = race.calculateDistancePossibleForTime(3)
        assertThat(result).isEqualTo(12)
    }
}