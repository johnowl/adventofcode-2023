package day2

import Cube
import CubeSet
import Game
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class GameTest {
    @Test
    fun `should calculate the fewest number of cubes of each color that could have been in the bag to make the game possible`() {
        val game = Game(1, listOf(
                CubeSet(setOf(Cube("blue", 3), Cube("red", 4))),
                CubeSet(setOf(Cube("red", 1), Cube("green", 2), Cube("blue", 6))),
                CubeSet(setOf(Cube("green", 2)))
            ))

        val result = game.getMaxOfEachColor()

        assertThat(result.cubes).containsExactlyInAnyOrder(
            Cube("red", 4),
            Cube("green", 2),
            Cube("blue", 6),
        )

    }
}