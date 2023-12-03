package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Part2KtTest {
    @Test
    fun `should return true if star has two adjacent numbers`(){
        val rawInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...+.*....
            .664.598..
        """.trimIndent()

        val input = parseInput(rawInput)
        val numbers = findAllNumberPositions(input).toSet()
        val starPosition = StarPosition(3, 1)

        val adjacentNumbers = listStarAdjacentNumbers(starPosition, numbers)

        assertThat(adjacentNumbers).containsExactlyInAnyOrder(
            NumberPosition(0, 0, 3),
            NumberPosition(2, 2, 2),
        )
    }

    @Test
    fun `should get number value from position`() {
        val input = listOf(
            listOf('4', '6', '.'),
            listOf('.', '.', '*'),
            listOf('.', '.', '3'),
        )

        val numberPosition = NumberPosition(0, 0, 2)

        val result = numberPosition.getNumber(input)

        assertThat(result).isEqualTo(46)
    }

    @Test
    fun `should solve it correctly`(){
        val rawInput = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...${'$'}.*....
            .664.598..
        """.trimIndent()

        val result = solvePart2(rawInput)

        assertThat(result).isEqualTo(467835)
    }
}