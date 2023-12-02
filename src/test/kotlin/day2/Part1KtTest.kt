package day2

import Cube
import CubeSet
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import parseLine
import sumGameIdsThatCanBePlayed

class Part1KtTest {

    @Test
    fun `should parse valid line`() {
        val line = "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"

        val parsed = parseLine(line)
        assertThat(parsed.id).isEqualTo(3)
        assertThat(parsed.revealed).containsExactlyInAnyOrder(
            CubeSet(setOf(Cube("green", 8), Cube("blue", 6), Cube("red", 20))),
            CubeSet(setOf(Cube("blue", 5), Cube("red", 4), Cube("green", 13))),
            CubeSet(setOf(Cube("green", 5), Cube("red", 1)))
        )
    }

    @Test
    fun `should sum id of playable games`() {

        val rawGames = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".split("\n")

        val parsedGames = rawGames.map { parseLine(it) }.toSet()

        val cubeSet = CubeSet(setOf(Cube("red", 12), Cube("green", 13), Cube("blue", 14)))

        val result = sumGameIdsThatCanBePlayed(cubeSet, parsedGames)

        assertThat(result).isEqualTo(8)
    }
}