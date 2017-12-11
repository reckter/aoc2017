package me.reckter.aoc

import java.util.ArrayDeque

class Day7 : Day {
    override val day: Int = 7

    val nodes = mutableMapOf<String, Node>()

    init {
        val stillToParse = ArrayDeque<String>()
        loadInput(7)
            .sortedBy { it.length }
            .forEach { stillToParse.add(it) }

        val normalRegex = Regex("(.*?) \\((.*)\\)$")
        val extendedRegex = Regex("(.*?) \\((.*)\\) -> (.*)$")

        while (stillToParse.isNotEmpty()) {
            val toParse = stillToParse.poll()


            if (normalRegex.matches(toParse)) {
                val (name, weight) = normalRegex.matchEntire(toParse)?.destructured ?: error("no match")
                nodes.put(name, Node(name, weight.toInt()))
            } else if (extendedRegex.matches(toParse)) {
                val (name, weight, childString) = extendedRegex.matchEntire(toParse)?.destructured ?: error("no match")
                val childrenName = childString.split(", ")
                val children = childrenName.map { nodes[it] }

                if (children.any { it == null }) {
                    stillToParse.add(toParse)
                } else {
                    nodes.put(name, Node(name, weight.toInt(), children.filterNotNull()))
                }
            }
        }
    }

    override fun solvePart1() {
        val root = nodes
            .values
            .find { node ->
                nodes.values.none {
                    it.children.any { it == node }
                }
            }

        println("solution 1: ${root?.name}")
    }

    override fun solvePart2() {
        nodes.values
            .filter { it.children.isNotEmpty() }
            .forEach { node ->
                val weights = node.children
                    .map { it.sumWeight }
                if (weights.distinct().size != 1) {
                    println(node)
                    val distinctWeights = weights.distinct()

                    val wrong = distinctWeights
                        .map { distinct ->
                            distinct to weights
                                .filter { distinct == it }
                                .count()
                        }
                        .minBy { it.second }?.first ?: error("no wrong")
                    val other = distinctWeights.find { it != wrong } ?: error("no other")

                    val index = weights.indexOf(wrong)
                    val diff = wrong - other
                    println(node.children[index].weight - diff)
                }
            }
    }
}

data class Node(
    val name: String,
    val weight: Int,
    val children: List<Node> = listOf()
) {
    val sumWeight: Int by lazy {
        weight + children.map { it.sumWeight }.sum()
    }
}

fun main(args: Array<String>) {
    val day = Day7()
    day.solvePart1()
    day.solvePart2()
}

