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
            .filter { it.distinct().size == it.size }
            .filter {
                it
                    .allCombinations()
                    .all {
                        val groupA = it.first.groupBy { it }
                        val groupB = it.second.groupBy { it }

                        groupA.size != groupB.size
                        || groupA.any { groupB[it.key] != it.value }
                    }
            }
            .count()
            .print("solution 2 : ")
    }
}

fun main(args: Array<String>) {
    val day = Day4()
    day.solvePart1()
    day.solvePart2()
}
