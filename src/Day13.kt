import kotlin.math.abs

enum class Axis {
    X,
    Y
}

data class Fold(val axis: Axis, val pos: Int)

fun main() {
    fun fold(map: Set<Pair<Int, Int>>, fold: Fold): Set<Pair<Int, Int>> {
        return map.map {
            if (fold.axis == Axis.X) {
                return@map Pair(fold.pos - abs(fold.pos - it.first), it.second)
            } else {
                return@map Pair(it.first, fold.pos - abs(fold.pos - it.second))
            }
        }.toSet()
    }

    fun part1(input: List<String>): Int {
        val map = mutableSetOf<Pair<Int, Int>>()
        var addingPoints = true
        val transformations = mutableListOf<Fold>()
        for (line in input) {
            if (line.isEmpty()) {
                addingPoints = false
                continue
            }

            if (addingPoints) {
                val split = line.split(",")
                map.add(Pair(split[0].toInt(), split[1].toInt()))
            } else {
                val foldRegex = "fold along (?<axis>[xy])=(?<pos>\\d+)".toRegex()
                val match = foldRegex.matchEntire(line) ?: continue
                val axis = if (match.groups["axis"]?.value == "x") {
                    Axis.X
                } else {
                    Axis.Y
                }

                val pos = match.groups["pos"]?.value.toString().toInt() // null causes numberformatexception

                transformations.add(Fold(axis, pos))
            }
        }
        val newMap = fold(map, transformations[0])
        return newMap.size
    }

    fun part2(input: List<String>) {
        var map = mutableSetOf<Pair<Int, Int>>()
        var addingPoints = true
        val transformations = mutableListOf<Fold>()
        for (line in input) {
            if (line.isEmpty()) {
                addingPoints = false
                continue
            }

            if (addingPoints) {
                val split = line.split(",")
                map.add(Pair(split[0].toInt(), split[1].toInt()))
            } else {
                val foldRegex = "fold along (?<axis>[xy])=(?<pos>\\d+)".toRegex()
                val match = foldRegex.matchEntire(line) ?: continue
                val axis = if (match.groups["axis"]?.value == "x") {
                    Axis.X
                } else {
                    Axis.Y
                }

                val pos = match.groups["pos"]?.value.toString().toInt() // null causes numberformatexception

                transformations.add(Fold(axis, pos))
            }
        }

        for (transformation in transformations) {
            println(transformation)
            map = fold(map, transformation).toMutableSet()
        }

        val maxX = map.maxOf { it.first }
        val maxY = map.maxOf { it.second }

        for (y in 0..maxY) {
            for (x in 0..maxX) {
                if (map.contains(Pair(x, y))) {
                    print("#")
                } else {
                    print(" ")
                }
            }
            println()
        }
        println()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    part2(testInput)

    val input = readInput("Day13")
    println(part1(input))
    part2(input)
}