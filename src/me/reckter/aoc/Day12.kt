package me.reckter.aoc

import java.util.ArrayDeque

class Day12 : Day {
    override val day: Int = 12

    val connections: Map<String, List<String>>

    init {
        connections = loadInput(12)
            .parseWithRegex("(.*?) <-> (.*?)$")
            .map { (id, connections) ->
                id to connections.split(", ").distinct()
            }
            .toMap()
    }

    fun getAllConnected(start: String): List<String>{
        val queue = ArrayDeque<String>(connections.size)
        val seen = mutableListOf<String>()
        queue.add(start)
        while (queue.isNotEmpty()) {
            val next = queue.poll()
            val neighbours = connections[next]
            queue.addAll(
                neighbours
                    ?.filter { it !in queue }
                    ?.filter { it !in seen }
                    ?.filter { it != next }
                    ?.distinct()
                    ?: listOf()
            )
            seen.add(next)
        }
        return seen
    }

    override fun solvePart1() {
        getAllConnected("0")
            .size
            .print("solution 1:")
    }

    override fun solvePart2() {
        val nodes = connections.keys.toList().toMutableList()
        val seen = mutableListOf<String>()
        var groups = 0
        while(nodes.isNotEmpty()) {
            val connected = getAllConnected(nodes.first())
            nodes.removeAll { it in connected }
            seen.addAll(connected)
            groups++
        }

        groups
            .print("solution 2:")

    }
}

fun main(args: Array<String>) {
    val day = Day12()
    day.solvePart1()
    day.solvePart2()
}
