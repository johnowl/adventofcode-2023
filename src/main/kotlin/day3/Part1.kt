package day3

import java.io.File

fun main() {
    File("src/main/resources/day3/input.txt")
        .readText()
        .let { input ->
            val result = solvePart1(input)
            println(result)
        }
}

fun solvePart1(input: String): Int {
    val parsedInput = parseInput(input)
    val numberPositions = findAllNumberPositions(parsedInput)
    return numberPositions.filter {
        numberHasAdjacentSymbols(parsedInput, it)
    }.sumOf {
        it.getNumber(parsedInput)
    }
}

fun parseInput(input: String): List<List<Char>> {
    val lines = input.split("\n")
    return lines.map { line ->
        line.split("")
            .filter { it.isNotEmpty() }
            .map { it.first() }
    }
}

data class NumberPosition(val x: Int, val y: Int, val length: Int) {
    fun getNumber(input: List<List<Char>>): Int =
        (0..<length).map { i ->
            input[y][x + i]
        }.joinToString("") {
            it.toString()
        }.toInt()
}

fun findAllNumberPositions(input: List<List<Char>>): List<NumberPosition> {
    val result = mutableListOf<NumberPosition>()

    var y = 0
    var x = 0

    while (y < input.size) {
        while (x < input[y].size) {
            if (input[y][x].isDigit()) {
                var length = 1
                while (x + length < input[y].size && input[y][x + length].isDigit()) {
                    length++
                }
                result.add(NumberPosition(x, y, length))
                x += length
            } else {
                x++
            }
        }
        x = 0
        y++
    }

    return result
}

fun numberHasAdjacentSymbols(input: List<List<Char>>, numberPosition: NumberPosition): Boolean {

    val y = numberPosition.y
    // this can be optimized, but I don't care now
    for (x in numberPosition.x..<numberPosition.x + numberPosition.length) {
        val left = isSymbol(x - 1, y, input)
        val right = isSymbol(x + 1, y, input)
        val top = isSymbol(x, y - 1, input)
        val bottom = isSymbol(x, y + 1, input)
        val topLeft = isSymbol(x - 1, y - 1, input)
        val topRight = isSymbol(x + 1, y - 1, input)
        val bottomLeft = isSymbol(x - 1, y + 1, input)
        val bottomRight = isSymbol(x + 1, y + 1, input)

        if (listOf(left, right, top, bottom, topLeft, topRight, bottomLeft, bottomRight).any { it }) {
            return true
        }
    }

    return false
}

private fun isSymbol(x: Int, y: Int, input: List<List<Char>>): Boolean {
    if (x < 0 || y < 0 || x >= input.size || y >= input[x].size) {
        return false
    }

    val char = input[y][x]
    return char.isDigit().not() && char != '.'
}