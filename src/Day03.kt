class BinaryCount(var ones: Int, var zeroes: Int)

fun main() {
    fun part1(input: List<String>): Int {
        val counts = mutableListOf<BinaryCount>()
        for (line in input) {
            for (i in line.indices) {
                if (counts.size <= i) {
                    counts.add(BinaryCount(0, 0))
                }

                if (line[i] == '0') {
                    counts[i].zeroes++
                } else {
                    counts[i].ones++
                }
            }
        }

        var gamma = ""
        var epsilon = ""
        for (count in counts) {
            if (count.ones > count.zeroes) {
                gamma += "1"
                epsilon += "0"
            } else {
                gamma += "0"
                epsilon += "1"
            }
        }

        return gamma.toInt(2) * epsilon.toInt(2)
    }

    fun part2(input: List<String>): Int {
        var lines = input

        var bits = ""
        var i = 0
        while (lines.size > 1) {
            for (line in lines) {
                bits += line[i]
            }

            val bc = BinaryCount(0, 0)
            for (bit in bits) {
                if (bit == '1') {
                    bc.ones++
                } else {
                    bc.zeroes++
                }
            }

            lines = if (bc.ones >= bc.zeroes) {
                lines.filter { it[i] == '1' }
            } else {
                lines.filter { it[i] == '0' }
            }

            i++

            bits = ""
        }

        val oxygen = lines[0].toInt(2)

        lines = input
        bits = ""
        i = 0
        while (lines.size > 1) {
            for (line in lines) {
                bits += line[i]
            }

            val bc = BinaryCount(0, 0)
            for (bit in bits) {
                if (bit == '1') {
                    bc.ones++
                } else {
                    bc.zeroes++
                }
            }

            lines = if (bc.ones >= bc.zeroes) {
                lines.filter { it[i] == '0' }
            } else {
                lines.filter { it[i] == '1' }
            }

            i++

            bits = ""
        }

        val co2 = lines[0].toInt(2)

        return co2 * oxygen
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
