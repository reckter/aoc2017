package me.reckter.aoc

class Day13 : Day {
    override val day: Int = 13

    fun getPenaltyForRun(scanners: List<Scanner>, delay: Int = 0): Int {
        return scanners
            .filter { scanner ->
                0 == (scanner.layer + delay) % (scanner.depth * 2 - 2)
            }.sumBy { it.depth * it.layer }
    }

    override fun solvePart1() {
        val scanners = loadInput(13)
            .parseWithRegex("(.*?): (.*?)$")
            .map { (layer, depth) -> Scanner(layer = layer.toInt(), depth = depth.toInt()) }

        getPenaltyForRun(scanners).print("solution 1: ")
    }

    override fun solvePart2() {
        val scanners = loadInput(13)
            .parseWithRegex("(.*?): (.*?)$")
            .map { (layer, depth) -> Scanner(layer = layer.toInt(), depth = depth.toInt()) }

        val solution =
            generateSequence(1) {
                it + 1
            }
                .first { delay ->
                    scanners.all { scanner ->
                        0 != (scanner.layer + delay) % (scanner.depth * 2 - 2)
                    }
                }
        solution.print("solution 2: ")
    }
}

data class Scanner(
    val layer: Int,
    val depth: Int,
    val position: Int = 0,
    val goingUp: Boolean = true
) {
    fun move(): Scanner {
        var newGoingUp = goingUp
        var newPos =
            if (goingUp)
                position + 1
            else
                position - 1
        if (newPos < 0 || newPos >= depth) {
            newGoingUp = !newGoingUp
            newPos = if (newPos < 0)
                1
            else
                depth - 2
        }
        return this.copy(
            position = newPos,
            goingUp = newGoingUp
        )
    }
}

fun main(args: Array<String>) {
    val day = Day13()
    day.solvePart1()
    day.solvePart2()
}

