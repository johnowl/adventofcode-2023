package day2

import Cube
import CubeSet
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Part2KtTest {

    @Test
    fun `should calculate power of set of cubes`() {
        val cubeSet = CubeSet(setOf(Cube("red", 4), Cube("green", 2), Cube("blue", 6)))
        val result = calculatePowerOfSetOfCubes(cubeSet)
        assertThat(result).isEqualTo(48)
    }

}