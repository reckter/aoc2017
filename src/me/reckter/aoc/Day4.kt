package me.reckter.aoc

class Day4 : Day {
    override val day: Int = 4

    override fun solvePart1() {
        loadInput(4)
            .map { it.split(" ") }
            .filter { it.distinct().size == it.size }
            .count()
            .print("solution 1: ")
    }

    override fun solvePart2() {
        loadInput(4)
            .map { it.split(" ") }
            .map { it.map { it.toList().distinct().sorted() } }
            .filter { it.distinct().size == it.size }
            .count()
            .print("solution 2: ")
    }
}

fun main(args: Array<String>) {
    val day = Day4()
    day.solvePart1()
    day.solvePart2()
}
