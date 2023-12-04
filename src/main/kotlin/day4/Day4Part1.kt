package day4

import java.io.File
import kotlin.math.pow

fun main() {
    File("src/main/resources/day4/input.txt")
        .readText()
        .let { input ->
            val result = solveDay4Part1(input)
            println(result)
        }
}

fun solveDay4Part1(input: String): Int =
    input.split("\n").map { parseCard(it) }.sumOf { it.calculatePoints() }

fun parseCard(input: String): Card {
    val colonPosition = input.indexOf(":")
    val cardId = input.substring(0, colonPosition)
    val numbers = input.substring(colonPosition + 1).split("|")
    val winningNumbers = parseNumbers(numbers[0])
    val yourNumbers = parseNumbers(numbers[1])
    return Card(cardId, winningNumbers, yourNumbers)
}

private fun parseNumbers(input: String): Set<Int> =
    input.split(" ").filter { it.isNotEmpty() }.map { it.toInt() }.toSet()

data class Card(val id: CardId, val winningNumbers: Set<Int>, val yourNumbers: Set<Int>) {

    var copies: Int = 1
        private set

    fun calculatePoints(): Int =
        2.0.pow(winningNumbers.intersect(yourNumbers).size - 1).toInt()

    fun incrementCopies(amount: Int) {
        copies += amount
    }
}