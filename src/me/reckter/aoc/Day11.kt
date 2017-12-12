package me.reckter.aoc

class Day11 : Day {
    override val day: Int = 11

    override fun solvePart1() {
        val endPoint = loadInput(11)
            .first()
            .split(",")
            .fold(Point(0, 0, 0)) { point, direction ->
                point.move(direction)
            }


        val distance = endPoint.distanceTo(Point(0,0,0))

        println("solution 1: $distance")
    }

    override fun solvePart2() {
        val points = loadInput(11)
            .first()
            .split(",")
            .fold(listOf(Point(0, 0, 0))) { points, direction ->
                points + points.last().move(direction)
            }

        val startPoint = Point(0,0,0)
        val farthest = points.maxBy { it.distanceTo(startPoint) }

        farthest?.distanceTo(startPoint)
            .print("solution 2:")
    }
}

/*     0
     1,-1
    \ n  /
  nw +--+ ne
0,-1/  0 \  1,0
  -+  0,0 +-
-1,0\    /  0, 1
  sw +--+ se
    / s 0\
      -1,1
*/
data class Point(
    val x: Int,
    val y: Int,
    val z: Int
) {
    fun move(direction: String): Point {
        return when (direction) {
            "n" -> this.copy(
                x = x + 1,
                y = y - 1
            )
            "ne" -> this.copy(
                x = x + 1,
                z = z + 1
            )
            "se" -> this.copy(
                y = y + 1,
                z = z + 1
            )
            "s" -> this.copy(
                x = x - 1,
                y = y + 1
            )
            "sw" -> this.copy(
                x = x - 1,
                z = z - 1
            )
            "nw" -> this.copy(
                y = y - 1,
                z = z - 1
            )
            else -> error("invalid direction: $direction")
        }
    }

    fun distanceTo(to: Point): Int {
        return listOf(
            Math.abs(to.x - this.x),
            Math.abs(to.y - this.y),
            Math.abs(to.z - this.z)
        ).max() ?: error("no max!")
    }
}

fun main(args: Array<String>) {
    val day = Day11()
    day.solvePart1()
    day.solvePart2()
}
