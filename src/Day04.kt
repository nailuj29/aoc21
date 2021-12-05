import java.lang.StringBuilder

class Board(lines: List<String>, val id: Int) {

    val board: MutableList<List<Int>> = mutableListOf()
    val state = mutableListOf<MutableList<Boolean>>()

    init {
        for (line in lines) {
            this.board.add(line.trim().split("\\s+".toRegex()).flatMap {
                if (it == "") {
                    return@flatMap listOf()
                }
               listOf(it.toInt())
            })
            this.state.add(mutableListOf(false, false, false, false, false))
        }

        if (board.size == 0) {
            board.removeAt(0)
        }
    }

    val won: Boolean
        get() {
            for (row in state) {
                var won = true
                for (value in row) {
                    won = won && value
                }

                if (won) {
                    return true
                }
            }

            for (i in 0 until 5) {
                var won = true
                for (row in state) {
                    won = won && row[i]
                }

                if (won) {
                    return true
                }
            }

            return false
        }

    val total: Int
        get() {
            var total = 0
            for (i in 0 until 5) {
                for (j in 0 until 5) {
                    if (!state[i][j]) {
                        total += board[i][j]
                    }
                }
            }
            return total
        }

    fun changeNumber(number: Int) {
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                if (board[i][j] == number) {
                    state[i][j] = true
                    return
                }
            }
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()

        for (i in 0 until 5) {
            for (j in 0 until 5) {
                val item = board[i][j]
                if (item < 10) {
                    stringBuilder.append(" ")
                }
                stringBuilder.append(item)

                stringBuilder.append(":")
                stringBuilder.append(if (state[i][j]) "t" else "f")
                stringBuilder.append(" ")
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (other is Board) {
            return other.id == id
        }

        return false
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }

        val boards = mutableListOf<Board>()

        val strings = mutableListOf<String>()

        var id = 0
        for (line in input.subList(2, input.size)) {
            if (line == "") {
                boards.add(Board(strings, id++))
                strings.clear()
            } else {
                strings.add(line)
            }
        }

        boards.add(Board(strings, id))


        for (i in numbers) {
            for (board in boards) {
                board.changeNumber(i)

                if (board.won) {
                    return board.total * i
                }
            }
        }

        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers = input[0].split(",").map { it.toInt() }

        var boards = mutableListOf<Board>()

        val strings = mutableListOf<String>()

        var id = 0
        for (line in input.subList(2, input.size)) {
            if (line == "") {
                boards.add(Board(strings, id++))
                strings.clear()
            } else {
                strings.add(line)
            }
        }

        boards.add(Board(strings, id))

        var lastCalled = -1
        var lastBoard = boards[0]
        while (boards.size > 1) {
            for (i in numbers) {
                for (board in boards) {
                    board.changeNumber(i)
                    lastBoard = board
                    lastCalled = i
                }

                boards = boards.filter { !it.won } as MutableList<Board>
            }
        }

        return lastBoard.total * lastCalled

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
