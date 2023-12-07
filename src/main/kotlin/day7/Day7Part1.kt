package day7

import java.io.File

val day7Part1TestInput = """
32T3K 765
T55J5 684
KK677 28
KTJJT 220
QQQJA 483
""".trimIndent()

data class Card(val label: String) {
    private val cardsOrder = listOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J").reversed()

    fun strength(): Int {
        val cardIndex = cardsOrder.indexOf(label)
        return cardIndex + 1
    }
}

enum class HandType(val power: Int) {
    FiveOfAKind(7), // AAAAA
    FourOfAKind(6), // AAAAB
    FullHouse(5),   // AAABB
    ThreeOfAKind(4),// AAABC
    TwoPair(3),     // AABBC
    OnePair(2),     // AABCD
    HighCard(1);    // ABCDE

    fun increment(sortedCards: List<List<Card>>): HandType {
        val cards = sortedCards.flatten()

        val totalJokers = cards.countJokers()
        if (totalJokers == 0) {
            return this
        }

        val amountOfJokersInHandType = when (this) {
            FiveOfAKind, HighCard -> cards.countJokers()
            FourOfAKind, ThreeOfAKind -> sortedCards[0].countJokers()
            TwoPair, FullHouse -> sortedCards[0].countJokers() + sortedCards[1].countJokers()
            OnePair -> sortedCards[0].countJokers()
        }
        val unusedJokers = totalJokers - amountOfJokersInHandType

        return when (this) {
            FiveOfAKind ->
                // AAAAA
                // JJJJJ
                FiveOfAKind

            FourOfAKind -> {
                // AAAAJ
                // AAAAB
                // JJJJA
                when (unusedJokers) {
                    0 -> when (amountOfJokersInHandType) {
                        0 -> FourOfAKind
                        4 -> FiveOfAKind
                        else -> error("Invalid amount of jokers")
                    }
                    1 -> FiveOfAKind
                    else -> error("Invalid amount of jokers")
                }
            }

            FullHouse -> {
                // AAABB
                // AAAJJ
                // JJJAA
                when (amountOfJokersInHandType) {
                    0 -> FullHouse
                    2 -> FiveOfAKind
                    3 -> FiveOfAKind
                    else -> error("Invalid amount of jokers")
                }
            }

            ThreeOfAKind -> {
                // AAABC
                // JJJBC
                // AAAJB
                when (unusedJokers) {
                    0 -> when (amountOfJokersInHandType) {
                        0 -> ThreeOfAKind
                        3 -> FourOfAKind
                        else -> error("Invalid amount of jokers")
                    }
                    1 -> FourOfAKind
                    else -> error("Invalid amount of jokers")
                }
            }

            TwoPair -> {
                // AABBC
                // AABBJ
                // AAJJB
                when (unusedJokers) {
                    0 -> when (amountOfJokersInHandType) {
                        0 -> TwoPair
                        1 -> ThreeOfAKind
                        2 -> FourOfAKind
                        else -> error("Invalid amount of jokers")
                    }
                    1 -> FullHouse
                    else -> error("Invalid amount of jokers")
                }
            }

            OnePair -> {
                // AABCD
                // AABCJ
                // JJABC
                when (unusedJokers) {
                    0 -> when (amountOfJokersInHandType) {
                        0 -> OnePair
                        2 -> ThreeOfAKind
                        else -> error("Invalid amount of jokers")
                    }
                    1 -> ThreeOfAKind
                    else -> error("Invalid amount of jokers")
                }
            }

            HighCard -> {
                // ABCDE
                // JABCD
                if (amountOfJokersInHandType == 1) OnePair else HighCard
            }
        }
    }

    private fun List<Card>.countJokers(): Int =
        this.count { it.label == "J" }
}

data class Hand(val cards: List<Card>, val bid: Long) {

    fun calculateHandType(): HandType {
        val groupedCards = cards.groupBy { it.label }
        val sortedCards = groupedCards.values.sortedByDescending { it.size }
        val handType = when (sortedCards.size) {
            1 -> HandType.FiveOfAKind
            2 -> {
                if (sortedCards[0].size == 4) {
                    HandType.FourOfAKind
                } else {
                    HandType.FullHouse
                }
            }

            3 -> {
                if (sortedCards[0].size == 3) {
                    HandType.ThreeOfAKind
                } else {
                    HandType.TwoPair
                }
            }

            4 -> HandType.OnePair
            else -> HandType.HighCard
        }

        return handType.increment(sortedCards)
    }

    fun print() {
        print("${this.cards.joinToString(separator = "") { it.label }},${this.calculateHandType()}")
    }

    fun calculateValue(): Long =
        cards.joinToString("") {
            "$0${it.strength()}".takeLast(2)
        }.toLong()

}

data class Game(val hands: List<Hand>) {
    private fun rankHands(): List<Hand> =
        hands.sortedBy { it.calculateHandType().power }
            .groupBy { it.calculateHandType() }
            .flatMap {
                it.value.sortedBy { hand ->
                    hand.calculateValue()
                }
            }

    fun calculateBids(): List<Long> =
        buildList {
            for ((index, hand) in rankHands().withIndex()) {
                val position = index + 1
                print("$position,")
                hand.print()
                println()
                val bid = hand.bid * position
                this.add(bid)
            }
        }

}

fun test() {
    val game = parse(day7Part1TestInput)
    game.calculateBids().sum().let(::println)
}

fun main() {
    test()
    File("src/main/resources/day7/input.txt")
        .readText()
        .let { input ->
            val game = parse(input)
            game.calculateBids().sum().let(::println)
        }
}

fun parse(input: String): Game =
    input.split("\n").map { line ->
        val (cards, bid) = line.split(" ").filter { it.isNotBlank() }
        val parsedCards = cards.split("").filter { it.isNotBlank() }.map(::Card)
        Hand(parsedCards, bid.toLong())
    }.let {
        Game(it)
    }



