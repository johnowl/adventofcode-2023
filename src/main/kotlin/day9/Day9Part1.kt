package day9

import java.io.File


val day9TestInput = """
0 3 6 9 12 15
1 3 6 10 15 21
10 13 16 21 30 45
""".trimIndent()

data class History(val values: List<Long>) {
    fun isAllZeroes(): Boolean {
        return this.values.count { it != 0L } == 0
    }
}

data class Report(val histories: List<History>)

fun parse(input: String): Report {
    val histories = input.split("\n").map { line ->
        History(line.split(" ").filter { it.isNotBlank() }.map(String::toLong))
    }

    return Report(histories)
}

fun calculateNextRows(histories: MutableList<History>): List<History> {
    val next = histories.last()
    if (next.isAllZeroes()) {
        return histories
    }

    val newValues = next.values.windowed(2, 1).map { pair ->
        val (left, right) = pair
        right - left
    }

    histories.add(History(newValues))

    return calculateNextRows(histories)
}

private fun Report.solvePart1(): Long {
    val extrapolatedLists = mutableListOf<List<History>>()
    for (history in this.histories) {
        extrapolatedLists.add(calculateNextRows(mutableListOf(history)))
    }

    val result = extrapolatedLists.sumOf { histories ->
        histories.reversed().windowed(2, 1) {
            val (left, right) = it
            val leftValue = left.values.last()
            val rightValue = right.values.last()
            (right.values as MutableList).add(leftValue + rightValue)
        }
        histories.first().values.last()
    }

    println(result)

    return result
}

fun main() {
    val report = parse(File("src/main/resources/day9/input.txt").readText())
    report.solvePart1().let(::println)
}