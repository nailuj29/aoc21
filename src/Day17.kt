import java.lang.Integer.max
import java.lang.Integer.min

fun main() {

    fun simulate(target1: Pair<Int, Int>, target2: Pair<Int, Int>, vel: Pair<Int, Int>): Pair<Boolean, Int?> {
        var pos = Pair(0, 0)
        var velocity = vel
        var maxy = Int.MIN_VALUE
        while (pos.second >= min(target1.second, target2.second) && pos.first <= max(
                target1.first,
                target2.first
            )
        ) {
            pos = Pair(pos.first + velocity.first, pos.second + velocity.second)
            velocity = Pair(max(velocity.first - 1, 0), velocity.second - 1)
            if (pos.second > maxy) {
                maxy = pos.second
            }

            if (min(target1.first, target2.first) <= pos.first && pos.first <= max(
                    target1.first,
                    target2.first
                ) && min(target1.second, target2.second) <= pos.second && pos.second <= max(
                    target1.second,
                    target2.second
                )
            ) {
                return Pair(true, maxy)
            }
        }

        return Pair(false, null)
    }


    val regex = "target area: x=(?<x1>-?\\d+)..(?<x2>-?\\d+), y=(?<y1>-?\\d+)..(?<y2>-?\\d+)".toRegex()
    fun part1(input: List<String>): Int {
        val matched = regex.matchEntire(input[0])!!
        val t1 = Pair(matched.groups["x1"]!!.value.toInt(), matched.groups["y1"]!!.value.toInt())
        val t2 = Pair(matched.groups["x2"]!!.value.toInt(), matched.groups["y2"]!!.value.toInt())

        var maxy = Int.MIN_VALUE
        for (x in 1..500) {
            for (y in -200..200) {
                val result = simulate(t1, t2, Pair(x, y))
                if (result.first) {
                    if (result.second!! > maxy) {
                        maxy = result.second!!
                    }
                }
            }
        }

        return maxy
    }

    fun part2(input: List<String>): Int {

        val matched = regex.matchEntire(input[0])!!
        val t1 = Pair(matched.groups["x1"]!!.value.toInt(), matched.groups["y1"]!!.value.toInt())
        val t2 = Pair(matched.groups["x2"]!!.value.toInt(), matched.groups["y2"]!!.value.toInt())

        var vels = 0
        for (x in 1..500) {
            for (y in -200..200) {
                val result = simulate(t1, t2, Pair(x, y))
                if (result.first) {
                    vels++
                }
            }
        }

        return vels
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day17_test")
    check(part1(testInput) == 45)
    check(part2(testInput) == 112)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
