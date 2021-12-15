fun main() {
    fun part1(input: List<String>): Int {
        val lanternfish = mutableListOf<Int>()

        lanternfish.addAll(input[0].split(",").map { it.toInt() })

        for (i in 0 until 80) {
            for (j in lanternfish.indices) {
                val time = lanternfish[j]
                if (time == 0) {
                    lanternfish.add(8)
                    lanternfish[j] = 6
                } else {
                    lanternfish[j] -= 1
                }
            }
        }

        return lanternfish.size
    }

    fun part2(input: List<String>): Long {
        val lanternfish = mutableListOf<Int>()

        lanternfish.addAll(input[0].split(",").map { it.toInt() })

        val fishCreationCounts = mutableMapOf<Int, Long>()
        for (fish in lanternfish) {
            fishCreationCounts[fish] = (fishCreationCounts[fish] ?: 0) + 1
        }

        var count = 0L + lanternfish.size
        for (i in 0 until 256) {
            count += fishCreationCounts[i] ?: 0
            fishCreationCounts[i + 7] = (fishCreationCounts[i + 7] ?: 0) + (fishCreationCounts[i] ?: 0)
            fishCreationCounts[i + 9] = (fishCreationCounts[i + 9] ?: 0) + (fishCreationCounts[i] ?: 0)
        }

        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
