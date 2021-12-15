import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.map { line -> line.toCharArray().map { it.toString().toInt() } }
        var total = 0
        for (i in grid.indices) {
            val row = grid[i]
            for (j in row.indices) {
                if (row[j] < grid.getOrEmpty(i + 1).getOrMax(j) && row[j] < grid.getOrEmpty(i - 1).getOrMax(j)
                    && row[j] < row.getOrMax(j + 1) && row[j] < row.getOrMax(j - 1)) {
                    total += row[j] + 1
                }
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        fun basinWithCenter(grid: List<List<Int>>, point: Pair<Int, Int>): Int {
            val queue = LinkedList<Pair<Int, Int>>()
            var nnode: Pair<Int, Int>? = point
            val width = grid.size
            val height = grid[0].size
            var count = 0
            val countedPoints = mutableListOf<Pair<Int, Int>>()
            val testGrid = grid.map { it.toMutableList() }.toMutableList()
            do {
                var x = nnode!!.first
                val y = nnode.second
                while (x > 0 && testGrid[x - 1][y] < 9) x--
                var spanUp = false
                var spanDown = false
                while (x < width && testGrid[x][y] < 9) {
                    if (!countedPoints.contains(Pair(x, y))) {
                        count++
                        countedPoints.add(Pair(x, y))
                        testGrid[x][y] = 9
                    }

                    if (!spanUp && y > 0 && testGrid[x][y - 1] < 9) {
                        queue.add(Pair(x, y - 1))
                        spanUp = true
                    } else if (spanUp && y > 0 && testGrid[x][y - 1] == 9) {
                        spanUp = false
                    }

                    if (!spanDown && y < height - 1 && testGrid[x][y + 1] < 9) {
                        queue.add(Pair(x, y + 1))
                        spanDown = true
                    } else if (spanDown && y < height - 1 && testGrid[x][y + 1] == 9) {
                        spanDown = false
                    }
                    x++
                }
                nnode = queue.pollFirst()
            } while (nnode != null)

            return count
        }

        val grid = input.map { line -> line.toCharArray().map { it.toString().toInt() } }
        val lowPoints = mutableListOf<Pair<Int, Int>>()
        for (i in grid.indices) {
            val row = grid[i]
            for (j in row.indices) {
                if (row[j] < grid.getOrEmpty(i + 1).getOrMax(j) && row[j] < grid.getOrEmpty(i - 1).getOrMax(j)
                    && row[j] < row.getOrMax(j + 1) && row[j] < row.getOrMax(j - 1)) {
                    lowPoints.add(Pair(i, j))
                }
            }
        }

        val biggestBasins = mutableListOf<Int>()
        for (point in lowPoints) {
            val basinSize = basinWithCenter(grid, point)
            if (biggestBasins.size < 3) {
                biggestBasins.add(basinSize)
            } else {
                if (biggestBasins.sorted().first() < basinSize) {
                    biggestBasins.remove(biggestBasins.sorted().first())
                    biggestBasins.add(basinSize)
                }
            }
        }

        return biggestBasins.reduce { acc, i -> acc * i }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
