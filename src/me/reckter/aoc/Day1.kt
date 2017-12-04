package me.reckter.aoc

class Day1 : Day {
    override val day: Int = 1

    override fun solvePart1() {
        loadInput(1)
            .first()
            .map { it.toString() }
            .toIntegers()
            .pairWithIndex { it + 1 }
            .filter { (it, following) -> it == following}
            .sumBy { it.first }
            .print("solution `Day1`: ")

    }

    override fun solvePart2() {
        loadInput(1)
            .first()
            .map { it.toString() }
            .toIntegers()
            .pairWithIndexAndSize { index, size -> index + size / 2 }
            .filter { (it, following) -> it == following}
            .sumBy { it.first }
            .print("solution 2: ")

    }
}

fun main(args: Array<String>) {
    val day = Day1()
    day.solvePart1()
    day.solvePart2()
}
