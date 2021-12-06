import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        fun pointsBetween(a: Pair<Int, Int>, b: Pair<Int, Int>): List<Pair<Int, Int>> {
            val output = mutableListOf<Pair<Int, Int>>()
            if (a.first == b.first || a.second == b.second) {
                if (a.first == b.first) {
                    for (i in min(a.second, b.second)..max(a.second, b.second)) {
                        output.add(Pair(a.first, i))
                    }
                }
                if (a.second == b.second) {
                    for (i in min(a.first, b.first)..max(a.first, b.first)) {
                        output.add(Pair(i, a.second))
                    }
                }
            }

            return output
        }

        val points = mutableListOf<Pair<Int, Int>>()

        for (line in input) {
            val pointsFromInput = line.split((" -> ")).map { point -> point.split(",").map { it.toInt() } }
            val a = Pair(pointsFromInput[0][0], pointsFromInput[0][1])
            val b = Pair(pointsFromInput[1][0], pointsFromInput[1][1])
            points.addAll(pointsBetween(a, b))
        }

        val foundPoints = mutableListOf<Pair<Int, Int>>()
        val duplicates = mutableListOf<Pair<Int, Int>>()
        for (point in points) {
            if (point !in foundPoints) {
                foundPoints.add(point)
            } else {
                if (point !in duplicates) {
                    duplicates.add(point)
                }
            }
        }

        return duplicates.size
    }

    fun part2(input: List<String>): Int {
        fun pointsBetween(a: Pair<Int, Int>, b: Pair<Int, Int>): List<Pair<Int, Int>> {
            val output = mutableListOf<Pair<Int, Int>>()
            if (a.first == b.first || a.second == b.second) {
                if (a.first == b.first) {
                    for (i in min(a.second, b.second)..max(a.second, b.second)) {
                        output.add(Pair(a.first, i))
                    }
                }
                if (a.second == b.second) {
                    for (i in min(a.first, b.first)..max(a.first, b.first)) {
                        output.add(Pair(i, a.second))
                    }
                }
            } else {
                if (a.first < b.first) {
                    if (a.second < b.second) {
                        var x = a.first
                        var y = a.second
                        for (i in 0..abs(a.first-b.first)) {
                            output.add(Pair(x, y))
                            x += 1
                            y += 1
                        }
                    } else {
                        var x = a.first
                        var y = a.second
                        for (i in 0..abs(a.first-b.first)) {
                            output.add(Pair(x, y))
                            x += 1
                            y -= 1
                        }
                    }
                } else {
                    if (a.second < b.second) {
                        var x = a.first
                        var y = a.second
                        for (i in 0..abs(a.first-b.first)) {
                            output.add(Pair(x, y))
                            x -= 1
                            y += 1
                        }
                    } else {
                        var x = a.first
                        var y = a.second
                        for (i in 0..abs(a.first-b.first)) {
                            output.add(Pair(x, y))
                            x -= 1
                            y -= 1
                        }
                    }
                }
            }

            return output
        }

        val points = mutableListOf<Pair<Int, Int>>()

        for (line in input) {
            val pointsFromInput = line.split((" -> ")).map { point -> point.split(",").map { it.toInt() } }
            val a = Pair(pointsFromInput[0][0], pointsFromInput[0][1])
            val b = Pair(pointsFromInput[1][0], pointsFromInput[1][1])
            points.addAll(pointsBetween(a, b))
        }

        val foundPoints = mutableListOf<Pair<Int, Int>>()
        val duplicates = mutableListOf<Pair<Int, Int>>()
        for (point in points) {
            if (point !in foundPoints) {
                foundPoints.add(point)
            } else {
                if (point !in duplicates) {
                    duplicates.add(point)
                }
            }
        }

        return duplicates.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
