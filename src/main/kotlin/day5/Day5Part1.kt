package day5

import java.io.File

class LocationFinder {
    tailrec fun findLocation(seed: Long, conversionMaps: List<ConversionMap>): Long =
        if (conversionMaps.size == 1) {
            conversionMaps[0].findDestinationId(seed)
        } else {
            findLocation(conversionMaps[0].findDestinationId(seed), conversionMaps.drop(1))
        }

}

data class Almanac(
    val seeds: List<Long>,
    val conversionMaps: List<ConversionMap>,
)

data class ConversionMap(
    val from: String,
    val to: String,
    val destinationSourceRanges: List<DestinationSourceRange>,
) {
    fun findDestinationId(sourceId: Long): Long {
        for (range in destinationSourceRanges) {
            if (range.isInSourceRange(sourceId)) {
               return range.destinationRangeStart + (sourceId - range.sourceRangeStart)
            }
        }
        return sourceId
    }
}

data class DestinationSourceRange(
    val destinationRangeStart: Long,
    val sourceRangeStart: Long,
    val rangeLength: Long,
) {
    fun isInSourceRange(sourceId: Long): Boolean =
        sourceId in sourceRangeStart..<(sourceRangeStart + rangeLength)
}

val day5TestInput = """
        seeds: 79 14 55 13

        seed-to-soil map:
        50 98 2
        52 50 48
        
        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15
        
        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4
        
        water-to-light map:
        88 18 7
        18 25 70
        
        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13
        
        temperature-to-humidity map:
        0 69 1
        1 0 69
        
        humidity-to-location map:
        60 56 37
        56 93 4
        """.trimIndent()


fun testPart1() {

    val almanac = parseAlmanac(day5TestInput)
    val locationFinder = LocationFinder()

    val result = almanac.seeds.minOfOrNull { seed ->
        locationFinder.findLocation(seed, almanac.conversionMaps)
    }

    println(result)
}


fun main() {
    testPart1()

    File("src/main/resources/day5/input.txt")
        .readText()
        .let { input ->
            val almanac = parseAlmanac(input)

            val locationFinder = LocationFinder()

            val result = almanac.seeds.minOfOrNull { seed ->
                locationFinder.findLocation(seed, almanac.conversionMaps)
            }

            println(result)
        }
}



fun parseAlmanac(input: String): Almanac {
    val almanacParts = input.split("\n\n")
    val seeds = parseSeeds(almanacParts[0])
    val conversionMaps = almanacParts.drop(1).map { parseConversionMap(it) }
    return Almanac(seeds, conversionMaps)
}

fun parseSeeds(input: String): List<Long> {
    val (_, seeds) = input.split(":", limit = 2)
    return seeds.split(" ").filter { it.isNotBlank() }.map { it.toLong() }
}

fun parseConversionMap(input: String): ConversionMap {
    val (name, ranges) = input.split(":", limit = 2)
    val (from, to) = name.substringBefore(" map").split(regex = Regex("-to-"))
    val rangesList = ranges.split("\n").filter { it.isNotBlank() }.map { parseRange(it) }
    return ConversionMap(from, to, rangesList)
}

fun parseRange(input: String): DestinationSourceRange {
    val (destinationRangeStart, sourceRangeStart, rangeLength) = input
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toLong() }

    return DestinationSourceRange(destinationRangeStart, sourceRangeStart, rangeLength)
}