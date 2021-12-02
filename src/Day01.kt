fun main() {
    fun part1(input: List<Int>): Int {
        var count = 0
        for (i in 0 until (input.size - 1))  {
            if (input[i] < input[i + 1]) {
                count++
            }
        }
        return count
    }

    fun part2(input: List<Int>): Int {
        var count = 0
        for (i in 0 until input.size - 3) {
            if (input[i] + input[i + 1] + input[i + 2] < input[i + 1] + input[i + 2] + input[i + 3]) {
                count++
            }
        }
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
