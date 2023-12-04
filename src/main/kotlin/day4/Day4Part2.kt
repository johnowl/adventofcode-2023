package day4

import java.io.File

typealias CardId = String

fun Card.calculateCopiesEarned(): Int =
    winningNumbers.intersect(yourNumbers).size

fun Card.idAsInt(): Int =
    id.substring(5).toInt()



fun countTotalScratchCards(cards: List<Card>): Int {
    val result = cards.toList()

    for ((index, currentCard) in result.withIndex()) {
        val currentCopies = currentCard.copies
        for (i in 1..currentCard.calculateCopiesEarned()) {
            result[i + index].incrementCopies(currentCopies)
        }
    }

    return result.sumOf { it.copies }
}

fun solveDay4Part2(input: String): Int =
    input
        .split("\n")
        .map { parseCard(it) }
        .let { countTotalScratchCards(it) }

fun main() {
    File("src/main/resources/day4/input.txt")
        .readText()
        .let(::solveDay4Part2)
        .let( ::println)
}