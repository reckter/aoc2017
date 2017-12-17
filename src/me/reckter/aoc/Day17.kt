package me.reckter.aoc

class Day17 : Day {
    override val day: Int = 17

    override fun solvePart1() {
        val steps = loadInput(17)
            .first()
            .toInt()

        val ringBuffer = mutableListOf(0)

        var index = 0
        repeat(2017) { it ->
            index = (index + steps) % ringBuffer.size
            ringBuffer.add(index + 1, it + 1)
            index++
        }

        println("solution 1: " + ringBuffer[index + 1])
    }

    override fun solvePart2() {
        val steps = loadInput(17)
            .first()
            .toInt()


        var index = 0
        var size = 1
        var value = -1
        repeat(50_000_000) { it ->
            index = (index + steps) % size
            if(index == 0)
                value = it + 1
            size++
            index++

        }

        println("solution 2: " + value)
    }
}

fun main(args: Array<String>) {
    val day = Day17()
    day.solvePart1()
    day.solvePart2()
}

