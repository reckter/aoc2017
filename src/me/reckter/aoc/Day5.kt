package me.reckter.aoc

class Day5 : Day {
    override val day: Int = 5

    override fun solvePart1() {
        val input = loadInput(5)
            .toIntegers()
            .toMutableList()
        var pointer = 0
        var steps = 0

        while (pointer in input.indices) {
            val oldIndex = pointer
            pointer += input[pointer]
            input[oldIndex]++
            steps++
        }

        println(" solution 1: $steps")
    }

    override fun solvePart2() {
        val input = loadInput(5)
            .toIntegers()
            .toMutableList()

        var pointer = 0
        var steps = 0

        while (pointer in input.indices) {
            val oldIndex = pointer
            pointer += input[pointer]
            if (input[oldIndex] >= 3)
                input[oldIndex]--
            else
                input[oldIndex]++
            steps++
        }

        println(" solution 1: $steps")
    }
}

private fun <T> List<T>.exchange(index: Int, t: T): List<T>
    = this.subList(0, index) + t + this.subList(index, this.size)

fun main(args: Array<String>) {
    val day = Day5()
    day.solvePart1()
    day.solvePart2()
}

