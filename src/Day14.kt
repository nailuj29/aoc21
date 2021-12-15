fun main() {
    fun part1(input: List<String>): Int {
        var template = input[0]

        val rules = mutableMapOf<String, String>()
        for (i in 2 until input.size) {
            val line = input[i].split(" -> ")
            rules[line[0]] = line[1]
        }

        for (i in 1..10) {
            val polymerBuilder = StringBuilder()
            for (pair in template.windowed(2)) {
                polymerBuilder.append(rules[pair])
            }

            var polymer = ""
            for (j in 0 until 2 * template.length - 1) {
                if (j % 2 == 0) {
                    polymer += template[j / 2]
                }

                if (j % 2 == 1) {
                    polymer += polymerBuilder[j / 2]
                }
            }
            template = polymer
        }

        var biggest = 0
        var smallest = Int.MAX_VALUE

        for (c in template.toCharArray().distinct()) {
            val count = template.count { it == c }
            if (count > biggest) {
                biggest = count
            }

            if (count < smallest) {
                smallest = count
            }
        }

        return biggest - smallest
    }

    fun part2(input: List<String>): Long {
        val template = input[0]

        val rules = mutableMapOf<String, String>()
        for (i in 2 until input.size) {
            val line = input[i].split(" -> ")
            rules[line[0]] = line[1]
        }
        var state = mutableMapOf<String, Long>()
        for (element in template.windowed(2)) {
            state[element] = (state[element] ?: 0) + 1
        }

        for (i in 1..40) {
            val newState = mutableMapOf<String, Long>()
            for (rule in rules.keys) {
                val result = rules[rule]!!
                if (state[rule] != null) {
                    newState[rule[0] + result] = (newState[rule[0] + result] ?: 0) + state[rule]!!
                    newState[result + rule[1]] = (newState[result + rule[1]] ?: 0) + state[rule]!!
                }
            }

            state = newState
        }

        val elementCounts = mutableMapOf<Char, Long>()
        for (key in state.keys) {
            elementCounts[key[0]] = (elementCounts[key[0]] ?: 0) + state[key]!!
            elementCounts[key[1]] = (elementCounts[key[1]] ?: 0) + state[key]!!
        }

        for (key in elementCounts.keys) {
            elementCounts[key] = elementCounts[key]!! / 2
        }

        elementCounts[template[0]] = elementCounts[template[0]]!! + 1
        elementCounts[template.last()] = elementCounts[template.last()]!! + 1

        val maximum = elementCounts.keys.map { elementCounts[it]!! }.maxOf { it }
        val minimum = elementCounts.keys.map { elementCounts[it]!! }.minOf { it }

        println(maximum)
        println(minimum)
        println(maximum - minimum)
        println(elementCounts)
        return maximum - minimum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588)
    check(part2(testInput) == 2188189693529)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
