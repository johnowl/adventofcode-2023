package day6

val testInputPart2 = """
    Time:      71530
    Distance:  940200
""".trimIndent()

val day6Part2Input = """
    Time:        51926890
    Distance:    222203111261225
""".trimIndent()

fun main() {
    val parsedInput = parse(day6Part2Input)
    solveItWithBruteForce(parsedInput)
}

