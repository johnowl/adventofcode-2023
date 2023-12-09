package day8

import java.io.File
import java.math.BigInteger


// too low: 1857219991
// 16342438708751
fun main() {
    val instructions = parseInput(File("src/main/resources/day8/input.txt").readText())
    instructions.solve()
    //println(instructions.solvePart2())
}

fun Instructions.solve() {
    val totalStepsToFinish = this.navigationMap.lines.filter {
        it.key.endsWith("A")
    }.map { it.value }
        .map { BigInteger.valueOf(solve(it).toLong()) }.toMutableList()

//    while (totalStepsToFinish.groupBy { it }.size > 1) {
//        for (i in totalStepsToFinish.indices) {
//            totalStepsToFinish[i] = totalStepsToFinish[i] + totalStepsToFinish[i]
//        }
//    }

    println(lcm(totalStepsToFinish))
}

fun lcm(inputs: List<BigInteger>): BigInteger {
    var input1 = inputs[0]
    var input2 = inputs[1]
    var lcm = lcm(input1, input2)

    for(index in 2..<inputs.size){
        input1 = lcm
        input2 = inputs[index]
        lcm = lcm(input1, input2)
    }

    return lcm
}

fun lcm(a: BigInteger, b: BigInteger): BigInteger {
    return a * b / gcd(a, b)
}

fun gcd(a: BigInteger, b: BigInteger): BigInteger {
    return if (b == BigInteger.ZERO) a else gcd(b, a % b)
}

fun Instructions.solve(start: MapLine): Int {
    var place = start
    val next = ""

    var direction = 0
    var steps = 0

    while(place.place.endsWith("Z").not()) {
        val next = if (directions[direction] == "R") {
            navigationMap.lines[place.right]!!
        } else {
            navigationMap.lines[place.left]!!
        }

        place = next

        steps++
        direction++
        if (direction >= directions.size) {
            direction = 0
        }
    }

    return steps
}


fun Instructions.solvePart2(): Int {
    val places = navigationMap.lines.filter { it.key.endsWith("A") }.map { it.value }.toMutableList()

    var steps = 0
    var direction = 0

    while (!places.all { it.place.endsWith("Z") }) {
        places.forEachIndexed { index, place ->
            val next = if (directions[direction] == "R") {
                navigationMap.lines[place.right]!!
            } else {
                navigationMap.lines[place.left]!!
            }
            places[index] = next
        }

        steps++
        direction++
        if (direction >= directions.size) {
            direction = 0
        }
    }

    return steps
}