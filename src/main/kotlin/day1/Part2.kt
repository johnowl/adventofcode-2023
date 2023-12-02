package day1

import java.io.File

fun main(args: Array<String>) {
    val result = File("src/main/resources/day1/input.txt")
        .readLines()
        .sumOf {
            "${it.firstDigit()}${it.lastDigit()}".toInt()
        }

    println(result)

    // 53846 = wrong
    // 53855
}


private val digits = listOf(
    "zero",
    "one", "two", "three", "four",
    "five", "six", "seven", "eight", "nine"
)

private fun String.firstDigit(): String {
    for (i in this.indices) {
        if (this[i].isDigit()) {
            return this[i].toString()
        }

       for (digit in digits) {
           if (this.substring(i).startsWith(digit)) {
               return (digits.indexOf(digit)).toString()
           }
       }
    }

    error("No digit found in string $this")
}


private fun String.lastDigit(): String {
    for (i in this.length - 1 downTo 0) {
        if (this[i].isDigit()) {
            return this[i].toString()
        }

        for (digit in digits) {
            if (this.substring(0, i + 1).endsWith(digit)) {
                return (digits.indexOf(digit)).toString()
            }
        }
    }

    error("No digit found in string $this")
}