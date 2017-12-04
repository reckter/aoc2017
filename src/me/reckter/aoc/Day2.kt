package me.reckter.aoc

class Day2 : Day {
    override val day: Int = 2

    override fun solvePart1() {
        loadInput(2)
            .map {
                it.split("\t")
                    .toIntegers()
            }
            .map { (it.max() ?: 0) - (it.min() ?: 0) }
            .sum()
            .print("solution 1:")
    }

    override fun solvePart2() {
        loadInput(2)
            .map {
                it.split("\t")
                    .toIntegers()
            }
            .map {
                it.allCombinations()
                    .find { (first, second) ->
                        first % second == 0
                    }
                    ?.let { (first, second) ->
                        first / second
                    } ?: 0
            }
            .sum()
            .print("solution 2:")
    }
}


fun main(args: Array<String>) {
    val day = Day2()
    day.solvePart1()
    day.solvePart2()
}
