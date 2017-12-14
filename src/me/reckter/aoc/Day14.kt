package me.reckter.aoc

import java.math.BigInteger
import java.util.ArrayDeque

class Day14 : Day {
    override val day: Int = 14

    override fun solvePart1() {
        val input = loadInput(14)
            .first()

        (0..127)
            .map { row ->
                KnotHash.hash(input + "-" + row)
            }
            .flatMap { hash ->
                hash.fromHexToBooleanList()
            }
            .count { it }
            .print("solution 1: ")
    }

    override fun solvePart2() {
        val input = loadInput(14)
            .first()

        val map = (0..127)
            .map { row ->
                KnotHash.hash(input + "-" + row)
            }
            .map { hash ->
                hash.fromHexToBooleanList()
            }

        val full = map
            .mapIndexed { x, list ->
                list.mapIndexed { y, value ->
                    (x to y).takeIf { value }
                }
            }
            .flatten()
            .filterNotNull()
            .toMutableList()

        val queue = ArrayDeque(full)

        val regions = mutableListOf<List<Pair<Int, Int>>>()

        while (queue.isNotEmpty()) {
            val next = queue.poll()

            val neighbours = next.getNeighbours(map)

            queue.removeAll(neighbours)
            regions.add(neighbours)
        }

        regions.size
            .print("solution 2: ")
    }
}

private fun Pair<Int, Int>.getNeighbours(map: List<List<Boolean>>): List<Pair<Int, Int>> {
    val queue = ArrayDeque<Pair<Int, Int>>(1000)
    queue.add(this)
    val seen = mutableListOf<Pair<Int, Int>>()
    while (queue.isNotEmpty()) {
        val next = queue.poll()
        val neighbours = (-1..1)
            .flatMap { x ->
                (-1..1).map { y ->
                    x to y
                }
            }
            .filter { (x, y) -> x == 0 || y == 0 }
            .map { (x, y) -> next.first + x to next.second + y }
            .filter { (x, y) -> x in map.indices && y in map[x].indices }
            .filter { (x, y) -> map[x][y]}
            .filter { it !in queue }
            .filter { it !in seen }
            .filter { it != next }
        queue.addAll(neighbours)
        seen.add(next)
    }

    return seen
}

private fun String.fromHexToBooleanList(): List<Boolean> {
    return BigInteger(this, 16)
        .toString(2)
        .padStart(128, '0')
        .map { it == '1' }
}

fun main(args: Array<String>) {
    val day = Day14()
    day.solvePart1()
    day.solvePart2()
}

