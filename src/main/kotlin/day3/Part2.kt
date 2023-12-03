package day3

import java.io.File


fun NumberPosition.contains(x: Int, y: Int): Boolean =
    x >= this.x && x < this.x + length && y == this.y

fun NumberPosition.id(): String =
    "$x$y"

fun findAllStarPositions(input: List<List<Char>>): Set<StarPosition> {
    val starPositions = mutableSetOf<StarPosition>()
    for (y in input.indices) {
        for (x in input[y].indices) {
            if (input[y][x] == '*') {
                starPositions.add(StarPosition(x, y))
            }
        }
    }
    return starPositions
}

fun listStarAdjacentNumbers(starPosition: StarPosition, numbers: Set<NumberPosition>): Set<NumberPosition> {
    val x = starPosition.x
    val y = starPosition.y

    val adjacentNumbers = mutableSetOf<NumberPosition>()
    for (number in numbers) {
        val topLeft = number.contains(x - 1, y - 1)
        val topRight = number.contains(x + 1, y - 1)
        val bottomLeft = number.contains(x - 1, y + 1)
        val bottomRight = number.contains(x + 1, y + 1)
        val left = number.contains(x - 1, y)
        val right = number.contains(x + 1, y)
        val top = number.contains(x, y - 1)
        val bottom = number.contains(x, y + 1)

        if (listOf(topLeft, topRight, bottomLeft, bottomRight, left, right, top, bottom).any { it }) {
            adjacentNumbers.add(number)
        }
    }

    return adjacentNumbers
}

data class StarPosition(val x: Int, val y: Int)

fun solvePart2(rawInput: String): Int {
    val input = parseInput(rawInput)
    val numbers = findAllNumberPositions(input).toSet()
    val starPositions = findAllStarPositions(input)

    return starPositions.sumOf {
        val adjacentNumbers = listStarAdjacentNumbers(it, numbers)
        val gearRatio = if (adjacentNumbers.size == 2) {
            val firstNumber = adjacentNumbers.first()
            val secondNumber = adjacentNumbers.last()
            firstNumber.getNumber(input) * secondNumber.getNumber(input)
        } else {
            0
        }
        gearRatio
    }
}

fun main() {
    File("src/main/resources/day3/input.txt")
        .readText()
        .let(::solvePart2)
        .let(::println)
}