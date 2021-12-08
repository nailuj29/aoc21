import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.round

fun main() {
    fun part1(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }.sorted()
        val target = if (positions.size % 2 == 0) {
            (positions[positions.size / 2] + positions[positions.size / 2 - 1]) / 2
        } else {
            positions[positions.size / 2]
        }

        val moves = positions.map { abs(it - target) }
        return moves.sum()
    }

    fun part2(input: List<String>): Int {
        val positions = input[0].split(",").map { it.toInt() }
        val min = positions.minOrNull()!!
        val max = positions.maxOrNull()!!

        val sums = mutableListOf<Int>()
        for (target in min..max) {
            val moves = positions.map { (1..abs(it - target)).sum() }
            sums.add(moves.sum())
        }

        return sums.minOrNull()!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
