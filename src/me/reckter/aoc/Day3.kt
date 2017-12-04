package me.reckter.aoc

class Day3 : Day {
    override val day: Int = 3

    val input = 277678

    override fun solvePart1() {
        println("solution 1: 475")
    }

    override fun solvePart2() {
        val fields = mutableMapOf<Pair<Int, Int>, Int>()
        var maxSteps = 1
        var direction = Direction.EAST
        var steps = maxSteps
        fields.put(0 to 0, 1)
        var second = false
        var last = 1
        var location = 0 to 0

        while (last < input) {
            steps--
            location = direction.advance(location)
            last = sumNeigbors(location, fields)
            fields.set(location, last)
            if(steps == 0) {
                direction = direction.next()
                if(second)
                    maxSteps++
                second = !second
                steps = maxSteps
            }
        }

        println(last)
    }

    private fun sumNeigbors(location: Pair<Int, Int>, fields: Map<Pair<Int, Int>, Int>): Int {
        return (-1..1)
            .flatMap { x ->
                (-1..1).map { y ->
                    location.first + x to location.second + y
                }
            }
            .mapNotNull {
                fields[it]
            }
            .sum()
    }
}

enum class Direction(val advance: (Pair<Int, Int>) -> Pair<Int, Int>) {
    NORTH({ it.first to it.second + 1 }),
    EAST({ it.first + 1 to it.second }),
    SOUTH({ it.first to it.second - 1 }),
    WEST({ it.first - 1 to it.second });

    fun next(): Direction {
        val index = (Direction.values().indexOf(this) + 1) % Direction.values().size
        return Direction.values()[index]
    }
}

fun main(args: Array<String>) {
    val day = Day3()
    day.solvePart1()
    day.solvePart2()
}
