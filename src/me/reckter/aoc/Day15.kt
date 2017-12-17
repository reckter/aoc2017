package me.reckter.aoc

class Day15 : Day {
    override val day: Int = 15

    lateinit var genA: Generator
    lateinit var genB: Generator

    fun reset () {

        loadInput(15)
            .parseWithRegex("Generator (.) starts with (.*)")
            .map { (name, value) ->
                if (name == "A")
                    genA = Generator(value.toLong(), 16807)
                else
                    genB = Generator(value.toLong(), 48271)
            }
        genA.generate()
        genB.generate()
    }

    override fun solvePart1() {
        reset()
        val mask = 0xFFFF.toLong()
        (1..40000000)
            .count { (genA.generate() and mask) == (genB.generate() and mask) }
            .print("solution 1: ")
    }

    override fun solvePart2() {
        reset()
        val mask = 0xFFFF.toLong()
        (1..5000000)
            .map {
                generateSequence { genA.generate() }
                    .first { it % 4 == 0L } to
                    generateSequence { genB.generate() }
                        .first { it % 8 == 0L }
            }
            .count { (first, second) ->
                (first and mask) == (second and mask)
            }
            .print("solution 1: ")
    }
}

fun main(args: Array<String>) {
    val day = Day15()
    day.solvePart1()
    day.solvePart2()
}

data class Generator(
    var value: Long,
    val multiplier: Long
) {
    fun generate(): Long {
        value = (value * multiplier) % 2147483647
        return value
    }
}
