package me.reckter.aoc

class Day10 : Day {
    override val day: Int = 10

    override fun solvePart1() {
        val input = loadInput(10)
            .first()
            .split(",")
            .toIntegers()
        val hash = KnotHash.oneHashRound(input).state
        println("solution 1:${hash[0] * hash[1]}")
    }

    override fun solvePart2() {
        val input = loadInput(10)
            .first()

        KnotHash.hash(input)
            .print("solution 2: ")
    }
}

fun main(args: Array<String>) {
    val day = Day10()
    day.solvePart1()
    day.solvePart2()
}

data class HashRound(
    val state: List<Int>,
    val index: Int,
    val skip: Int
)
