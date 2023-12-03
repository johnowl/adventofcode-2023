package day3

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Part1KtTest {

    @Test
    fun `should solve example`() {
        val input = """
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

        val result = solvePart1(input)

        assertThat(result).isEqualTo(4361)
    }

    @Test
    fun `should parse valid input`() {
        val input = """
           46.
           ..*
           ..3
        """.trimIndent()

        val result = parseInput(input)

        assertThat(result).containsExactlyInAnyOrder(
            listOf('4', '6', '.'),
            listOf('.', '.', '*'),
            listOf('.', '.', '3'),
        )
    }

    @Test
    fun `should find all number positions`() {
        val input = listOf(
            listOf('4', '6', '.'),
            listOf('.', '.', '*'),
            listOf('.', '.', '3'),
        )

        val result = findAllNumberPositions(input)

        assertThat(result).containsExactlyInAnyOrder(
            NumberPosition(0, 0, 2),
            NumberPosition(2, 2, 1),
        )
    }

    @Test
    fun `should get number value at position`() {
        val input = listOf(
            listOf('4', '6', '.'),
            listOf('.', '.', '*'),
            listOf('.', '.', '3'),
        )
        val position = NumberPosition(0, 0, 2)

        val result = position.getNumber(input)

        assertThat(result).isEqualTo(46)
    }

    @Test
    fun `should return true when number has adjacent symbol in top position`() {
        val input = listOf(
            listOf('4', '6', '.'),
            listOf('.', '.', '*'),
            listOf('.', '.', '3'),
        )
        val numberPosition = NumberPosition(2, 2, 1)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when number has adjacent symbol in bottom position`() {
        val input = listOf(
            listOf('4', '6', '.'),
            listOf('*', '.', '.'),
            listOf('.', '.', '*'),
        )
        val numberPosition = NumberPosition(0, 0, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when number has adjacent symbol in bottom-left position`() {
        val input = listOf(
            listOf('.', '6', '4'),
            listOf('*', '.', '.'),
            listOf('.', '.', '3'),
        )
        val numberPosition = NumberPosition(1, 0, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when number has adjacent symbol in bottom-right position`() {
        val input = listOf(
            listOf('4', '6', '.'),
            listOf('.', '.', '*'),
            listOf('.', '.', '3'),
        )
        val numberPosition = NumberPosition(0, 0, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when number has adjacent symbol in top-left position`() {
        val input = listOf(
            listOf('*', '.', '.'),
            listOf('.', '6', '4'),
            listOf('.', '.', '.'),
        )
        val numberPosition = NumberPosition(1, 1, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when number has adjacent symbol in top-right position`() {
        val input = listOf(
            listOf('.', '.', '*'),
            listOf('6', '4', '.'),
            listOf('.', '.', '.'),
        )
        val numberPosition = NumberPosition(0, 1, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when number has adjacent symbol in left position`() {
        val input = listOf(
            listOf('*', '6', '4'),
            listOf('.', '.', '.'),
            listOf('.', '.', '3'),
        )
        val numberPosition = NumberPosition(1, 0, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return true when number has adjacent symbol in right position`() {
        val input = listOf(
            listOf('4', '6', '*'),
            listOf('.', '.', '.'),
            listOf('.', '.', '3'),
        )
        val numberPosition = NumberPosition(0, 0, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when number has no adjacent symbols`() {
        val input = listOf(
            listOf('4', '6', '.'),
            listOf('.', '.', '.'),
            listOf('.', '.', '*'),
        )
        val numberPosition = NumberPosition(0, 0, 2)

        val result = numberHasAdjacentSymbols(input, numberPosition)

        assertThat(result).isFalse()
    }
}