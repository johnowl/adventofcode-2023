package day9

import java.io.File

fun calculateNextRowsReversed(histories: MutableList<History>): List<History> {
    val next = histories.last()
    if (next.isAllZeroes()) {
        return histories
    }

    val newValues = next.values.windowed(2, 1).map { pair ->
        val (left, right) = pair
        right - left
    }

    histories.add(History(newValues))

    return calculateNextRowsReversed(histories)
}

private fun Report.solvePart2(): Long {
    val extrapolatedLists = mutableListOf<List<History>>()
    for (history in this.histories) {
        extrapolatedLists.add(calculateNextRowsReversed(mutableListOf(history)))
    }

    val result = extrapolatedLists.sumOf { histories ->
        histories.reversed().windowed(2, 1) {
            val (left, right) = it
            val leftValue = left.values.first()
            val rightValue = right.values.first()
            (right.values as MutableList).add(0,  rightValue - leftValue)
        }
        histories.first().values.first()
    }

    println(result)

    return result
}

fun main() {
    val report = parse(File("src/main/resources/day9/input.txt").readText())
    report.solvePart2().let(::println)
}

// 2110 = too high
// 1152 = correct