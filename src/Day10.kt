import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        var totalScore = 0
        for (line in input) {
            val charStack = Stack<Char>()

            for (c in line) {
                when (c) {
                    '(', '[', '{', '<' -> charStack.push(c)
                    ')', ']', '}', '>' -> {
                        val popped = charStack.pop()
                        val expected = when (c) {
                            ')' -> '('
                            ']' -> '['
                            '}' -> '{'
                            '>' -> '<'
                            else -> 0
                        }
                        if (popped != expected) {
                            totalScore += when (c) {
                                ')' -> 3
                                ']' -> 57
                                '}' -> 1197
                                '>' -> 25137
                                else -> 0
                            }
                            break
                        }
                    }
                }
            }
        }
        return totalScore
    }

    fun part2(input: List<String>): Long {
        val scores = mutableListOf<Long>()
        outer@for (line in input) {
            val charStack = Stack<Char>()

            for (c in line) {
                when (c) {
                    '(', '[', '{', '<' -> charStack.push(c)
                    ')', ']', '}', '>' -> {
                        val popped = charStack.pop()
                        val expected = when (c) {
                            ')' -> '('
                            ']' -> '['
                            '}' -> '{'
                            '>' -> '<'
                            else -> 0
                        }
                        if (popped != expected) {
                            continue@outer
                        }
                    }
                }
            }

            if (charStack.size != 0) {
                var score: Long = 0
                for (item in charStack.reversed()) {
                    score *= 5
                    score += when (item) {
                        '(' -> 1
                        '[' -> 2
                        '{' -> 3
                        '<' -> 4
                        else -> 0
                    }
                }
                if (score != 0L) {
                    scores.add(score)
                }
            }
        }

        check(scores.size % 2 == 1)
        return scores.sorted()[scores.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
