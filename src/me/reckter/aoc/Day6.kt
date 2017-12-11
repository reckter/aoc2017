package me.reckter.aoc

class Day6: Day {
    override val day: Int = 6

    override fun solvePart1() {
        val ram = loadInput(6)
            .first()
            .split("\t")
            .toIntegers()

        val oldStates = generateLoopStates(ram)
        println("solution 1: ${oldStates.size}")

    }

    fun generateLoopStates(input: List<Int>): List<List<Int>> {

        val ram = input.toMutableList()

        val oldStates = mutableListOf<MutableList<Int>>()

        while(ram !in oldStates) {
            oldStates.add(ram.map { it }.toMutableList())
            val value = ram.max() ?: error("no max")
            val index = ram.indexOf(value)
            ram[index] = 0
            (1..value)
                .map { (it + index) % ram.size }
                .forEach { ram[it]++ }
        }
        return oldStates
    }
    override fun solvePart2() {
        val ram = loadInput(6)
            .first()
            .split("\t")
            .toIntegers()

        val oldStates = generateLoopStates(ram)
        println("solution 2: ${oldStates.size - oldStates.indexOf(ram)}")
    }
}


fun main(args: Array<String>) {
    val day = Day6()
    day.solvePart1()
    day.solvePart2()
}

