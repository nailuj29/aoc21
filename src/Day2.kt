fun main() {
    fun part1(input: List<String>): Int {
        var depth = 0
        var horizontal = 0

        for (command in input) {
            val split = command.split(" ")
            if (split[0] == "forward") {
                horizontal += split[1].toInt()
            } else if (split[0] == "down") {
                depth += split[1].toInt()
            } else if (split[0] == "up") {
                depth -= split[1].toInt()
            }
        }

        return depth * horizontal
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var horizontal = 0
        var aim = 0

        for (command in input) {
            val split = command.split(" ")
            if (split[0] == "forward") {
                horizontal += split[1].toInt()
                depth += split[1].toInt() * aim
            } else if (split[0] == "down") {
                aim += split[1].toInt()
            } else if (split[0] == "up") {
                aim -= split[1].toInt()
            }
        }

        return depth * horizontal
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
