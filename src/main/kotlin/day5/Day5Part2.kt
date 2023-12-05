package day5

import java.io.File

fun Almanac.solvePart2(): Long {
    val locationFinder = LocationFinder()
    var lowestLocation = Long.MAX_VALUE

    for (i in seeds.indices step 2) {
        val start = seeds[i]
        val length = seeds[i + 1]
        for (j in 0..<length) {
            val seed = start + j
            val location = locationFinder.findLocation(seed, conversionMaps)
            if (location < lowestLocation) {
                lowestLocation = location
            }
        }
    }

    return lowestLocation
}

fun testPart2() {
    val almanac = parseAlmanac(day5TestInput)
    val result = almanac.solvePart2()
    println(result)
}

fun main() {
    testPart2()
    // 14:45
    File("src/main/resources/day5/input.txt")
        .readText()
        .let { input ->
            val almanac = parseAlmanac(input)
            val result = almanac.solvePart2()
            println(result)
        }
}