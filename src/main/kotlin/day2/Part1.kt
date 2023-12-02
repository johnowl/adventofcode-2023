import java.io.File

fun main() {
    File("src/main/resources/day2/input.txt")
        .readLines()
        .map { parseLine(it) }
        .toSet()
        .let { games ->
            val cubeSet = CubeSet(setOf(Cube("red", 12), Cube("green", 13), Cube("blue", 14)))
            val result = sumGameIdsThatCanBePlayed(cubeSet, games)
            println(result)
        }
}

fun parseLine(line: String): Game {
    val (id, cubes) = line.split(":")
    val cubesList = cubes.split(";").map { it.trim() }
    val revealed = cubesList.map { parseCubeSet(it) }

    return Game(id.replace("Game ", "").toInt(), revealed)
}

fun sumGameIdsThatCanBePlayed(cubeSet: CubeSet, games: Set<Game>): Int =
    games.filter {
        gameIsPlayable(it, cubeSet)
    }.sumOf {
        it.id
    }

private fun gameIsPlayable(game: Game, cubeSet: CubeSet): Boolean =
    game.revealed.flatMap {
        it.cubes
    }.all {
        val maxAmount = cubeSet.cubes.find { cube -> cube.color == it.color }?.amount
            ?: error("Cube color ${it.color} not found in cube set")

        it.amount <= maxAmount
    }

fun parseCubeSet(cubes: String): CubeSet {
    val set = cubes.split(",")
        .map {
            val (amount, color) = it.trim().split(" ")
            Cube(color, amount.toInt())
        }.toSet()
    return CubeSet(set)
}

data class Game(val id: Int, val revealed: List<CubeSet>) {
    fun getMaxOfEachColor(): CubeSet {
        val result = revealed
            .flatMap { it.cubes }
            .groupBy { it.color }
            .map {
                it.value.maxBy { cube -> cube.amount }
            }.toSet()

        return CubeSet(result)
    }
}
data class Cube(val color: String, val amount: Int)
data class CubeSet(val cubes: Set<Cube>)