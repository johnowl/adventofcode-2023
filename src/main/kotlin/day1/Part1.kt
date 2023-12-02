package day1

import java.io.File

fun main(args: Array<String>) {
    val result = File("src/main/resources/day1/input.txt")
        .readLines()
        .sumOf {
            "${it.firstDigit()}${it.lastDigit()}".toInt()
        }

    println(result)
}

private fun String.firstDigit(): Char = this.find {
    it.isDigit()
} ?: error("No digit found in string")


private fun String.lastDigit(): Char = this.findLast {
    it.isDigit()
} ?: error("No digit found in string")