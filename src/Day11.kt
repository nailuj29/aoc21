fun main() {
    fun flash(grid: MutableList<MutableList<Int>>, flashes: MutableList<Pair<Int, Int>>, point: Pair<Int, Int>) {
        if (flashes.contains(point)) {
            return
        }
        if (grid[point.first][point.second] > 9) {
            flashes.add(Pair(point.first, point.second))

            if (point.first != 0) {
                if (point.second != 0) {
                    grid[point.first - 1][point.second - 1] += 1
                    flash(grid, flashes, Pair(point.first - 1, point.second - 1))
                }
                grid[point.first - 1][point.second] += 1
                flash(grid, flashes, Pair(point.first - 1, point.second))
                if (point.second + 1 < grid[point.first].size) {
                    grid[point.first - 1][point.second + 1] += 1
                    flash(grid, flashes, Pair(point.first - 1, point.second + 1))
                }
            }

            if (point.second != 0) {
                grid[point.first][point.second - 1] += 1
                flash(grid, flashes, Pair(point.first, point.second - 1))
            }

            if (point.second + 1 < grid[point.first].size) {
                grid[point.first][point.second + 1] += 1
                flash(grid, flashes, Pair(point.first, point.second + 1))
            }

            if (point.first + 1 < grid.size) {
                if (point.second != 0) {
                    grid[point.first + 1][point.second - 1] += 1
                    flash(grid, flashes, Pair(point.first + 1, point.second - 1))
                }
                grid[point.first + 1][point.second] += 1
                flash(grid, flashes, Pair(point.first + 1, point.second))
                if (point.second + 1 < grid[point.first].size) {
                    grid[point.first + 1][point.second + 1] += 1
                    flash(grid, flashes, Pair(point.first + 1, point.second + 1))
                }
            }
        }
    }

    fun part1(input: List<String>): Int {

        var flashCount = 0
        val octopusGrid = input.map { line -> line.toCharArray().map { it.toString().toInt() }.toMutableList() }.toMutableList()
        for (i in 1..100) {

            val flashed = mutableListOf<Pair<Int, Int>>()
            for (j in octopusGrid.indices) {
                val row = octopusGrid[j]
                for (k in row.indices) {
                    octopusGrid[j][k] += 1
                    flash(octopusGrid, flashed, Pair(j, k))
                }
            }

            for (flash in flashed) {
                octopusGrid[flash.first][flash.second] = 0
            }
            flashCount += flashed.size
        }
        return flashCount
    }

    fun part2(input: List<String>): Int {

        var allFlashed = false
        var i = 0
        val octopusGrid = input.map { line -> line.toCharArray().map { it.toString().toInt() }.toMutableList() }.toMutableList()
        while (!allFlashed) {

            val flashed = mutableListOf<Pair<Int, Int>>()
            for (j in octopusGrid.indices) {
                val row = octopusGrid[j]
                for (k in row.indices) {
                    octopusGrid[j][k] += 1
                    flash(octopusGrid, flashed, Pair(j, k))
                }
            }

            for (flash in flashed) {
                octopusGrid[flash.first][flash.second] = 0
            }

            if (flashed.size == octopusGrid.map { it.size }.sum()) {
                allFlashed = true
            }
            i++
        }
        return i
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
