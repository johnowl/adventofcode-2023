package day8

import java.io.File

val testInputDay8Part1 = """
LLR

AAA = (BBB, BBB)
BBB = (AAA, ZZZ)
ZZZ = (ZZZ, ZZZ)
""".trimIndent()

val testInputDay8Part2 = """
    LR
    
    11A = (11B, XXX)
    11B = (XXX, 11Z)
    11Z = (11B, XXX)
    22A = (22B, XXX)
    22B = (22C, 22C)
    22C = (22Z, 22Z)
    22Z = (22B, 22B)
    XXX = (XXX, XXX)
""".trimIndent()

fun main() {
    val instructions = parseInput(File("src/main/resources/day8/input.txt").readText())
    println(instructions.solvePart1("AAA", "ZZZ"))
}

fun parseInput(input: String): Instructions {
    val (directionsRaw, mapRaw) = input.split("\n\n")
    val directions = directionsRaw.split("").filter { it.isNotBlank() }
    val map = parseMap(mapRaw)
    return Instructions(directions, map)
}

data class MapLine(val place: String, val left: String, val right: String)
data class NavigationMap(val lines: Map<String, MapLine>)
data class Instructions(val directions: List<String>, val navigationMap: NavigationMap) {

    fun solvePart1(from: String, to: String): Int {

        var reachedDestination = false
        var next = from
        var direction = 0
        var steps = 0
        while (!reachedDestination) {
            val current = navigationMap.lines[next]
                ?: error("No such place: $next")

            if (current.place == to) {
                reachedDestination = true
            } else {
                next = if (directions[direction] == "R") {
                    current.right
                } else {
                    current.left
                }
                direction++
                steps++
                if (direction >= directions.size) {
                    direction = 0
                }
            }
        }
        return steps
    }
}

fun parseMap(input: String): NavigationMap =
    input.split("\n").map { line ->
        val (place, destinations) = line
            .replace("(", "")
            .replace(")", "")
            .split(" = ")
        val (left, right) = destinations.split(", ")
        place to MapLine(place, left, right)
    }.toMap().let(::NavigationMap)

