package day6

val testInput = """
    Time:      7  15   30
    Distance:  9  40  200
""".trimIndent()

val day6Part1Input = """
    Time:        51     92     68     90
    Distance:   222   2031   1126   1225
""".trimIndent()

fun main() {
    val parsedInput = parse(day6Part1Input)
    solveItWithBruteForce(parsedInput)
}

fun solveItWithBruteForce(races: List<Race>) {
    races.map { race ->
        (0..race.time).map { timeCharging ->
            val distance = race.calculateDistancePossibleForTime(timeCharging)
            if (distance > race.distance) {
                distance
            } else {
                null
            }
        }
    }.map {
        it.filterNotNull().count()
    }.fold(1) {
        acc, i -> acc * i
    }.let(::println)
}

fun parse(input: String): List<Race> {
    val (time, distance) = input.split("\n")

    val times = time.split(" ").drop(1).filter(String::isNotBlank).map(String::toLong)
    val distances = distance.split(" ").drop(1).filter(String::isNotBlank).map(String::toLong)

    return buildList {
        for (i in times.indices) {
            this.add(Race(times[i], distances[i]))
        }
    }
}

data class Race(val time: Long, val distance: Long) {
    fun calculateDistancePossibleForTime(timeCharging: Long): Long {
        if (timeCharging == 0L) {
            return 0
        }
        // timeCharging = speed
        return timeCharging * (this.time - timeCharging)
    }
}