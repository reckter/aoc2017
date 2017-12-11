@file:Suppress("UNUSED_EXPRESSION")

package me.reckter.aoc

class Day9 : Day {
    override val day: Int = 9

    override fun solvePart1() {
        val input = loadInput(9)
            .first()

        val groupStack = mutableListOf<Int>()

        var index = 0
        var inGarbage = false
        var sum = 0
        var garbageChars = 0
        while (index in input.indices) {
            val char = input[index]
            when (char) {
                '!' -> {
                    index++
                    garbageChars--
                }
                ',' ->  char
                '<' -> if (!inGarbage) {
                    inGarbage = true
                    garbageChars--
                }
                '>' -> if (inGarbage) inGarbage = false
                '{' -> if (!inGarbage) groupStack.add(index)
                '}' ->
                    if (!inGarbage) {
                        groupStack.removeAt(groupStack.size - 1)
                        sum += groupStack.size + 1
                    }
                else -> if(!inGarbage) error("not in garbage, but received $char")
            }
            if(inGarbage)
                garbageChars ++
            index++
        }

        println("solution 1: $sum")
        println("solution 2: $garbageChars")
    }

    override fun solvePart2() {
    }
}

fun main(args: Array<String>) {
    val day = Day9()
    day.solvePart1()
    day.solvePart2()
}

