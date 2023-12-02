package day2

import CubeSet
import parseLine
import java.io.File

fun main() {
    val result = File("src/main/resources/day2/input.txt")
        .readLines()
        .map { parseLine(it) }
        .sumOf {
            calculatePowerOfSetOfCubes(it.getMaxOfEachColor())
        }

    println(result)
}

fun calculatePowerOfSetOfCubes(cubeSet: CubeSet): Int =
    cubeSet.cubes.map { it.amount }.reduce { acc, i -> acc * i }