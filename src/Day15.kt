fun main() {
    fun dijkstra(grid: List<List<Int>>): Int {
        val dist = mutableMapOf<Pair<Int, Int>, Int>()
        val vertexSet = mutableSetOf<Pair<Int, Int>>()

        val current = Pair(0, 0)
        val target = Pair(grid[0].size - 1, grid.size - 1)

        for (y in 0 until grid.size) {
            for (x in 0 until grid[y].size) {
                dist[Pair(x, y)] = Int.MAX_VALUE
                vertexSet.add(Pair(x, y))
            }
        }
        dist[current] = 0

        while (vertexSet.size > 0) {
            val vertex = vertexSet.minByOrNull { dist[it]!! }!!

            vertexSet.remove(vertex)

            val neighbors = mutableListOf<Pair<Int, Int>>()

            if (vertex.first + 1 < grid[0].size) {
                neighbors.add(Pair(vertex.first + 1, vertex.second))
            }

            if (vertex.second + 1 < grid.size) {
                neighbors.add(Pair(vertex.first, vertex.second + 1))
            }

            if (vertex.first > 0) {
                neighbors.add(Pair(vertex.first - 1, vertex.second))
            }

            if (vertex.second > 0) {
                neighbors.add(Pair(vertex.first, vertex.second - 1))
            }

            for (neighbor in neighbors.filter { it in vertexSet }) {
                val alt = dist[vertex]!! + grid[neighbor.second][neighbor.first]
                if (alt < dist[neighbor]!!) {
                    dist[neighbor] = alt
                }
            }
        }

        return dist[target]!!
    }

    fun part1(input: List<String>): Int {
        val grid = input.map { line -> line.toCharArray().map { it.toString().toInt() } }

        val dist = dijkstra(grid)

        return dist
    }

    fun part2(input: List<String>): Int {
        fun tile(original: List<List<Int>>): List<List<Int>> {
            val tiles = mutableListOf(
                mutableListOf(listOf<List<Int>>(), listOf(), listOf(), listOf(), listOf()),
                mutableListOf(listOf(), listOf(), listOf(), listOf(), listOf()),
                mutableListOf(listOf(), listOf(), listOf(), listOf(), listOf()),
                mutableListOf(listOf(), listOf(), listOf(), listOf(), listOf()),
                mutableListOf(listOf(), listOf(), listOf(), listOf(), listOf())
            )

            for (r in 0 until 5) {
                for (c in 0 until 5) {
                    tiles[c][r] = original.map { row -> row.map inner@{
                        val value = it + r + c
                        if (value > 9) {
                            return@inner value - 9
                        } else {
                            return@inner value
                        }
                    } }
                }
            }

            val final = mutableListOf<MutableList<Int>>()
            for (i in 1..original.size * 5) {
                final.add(mutableListOf())
            }
            tiles.forEachIndexed { rowIndex, row ->
                row.forEachIndexed { _, grid ->
                    grid.forEachIndexed { gridRowIndex, gridRow ->
                        final[rowIndex * original.size + gridRowIndex].addAll(gridRow)
                    }
                }
            }

            return final
        }
        val grid = input.map { line -> line.toCharArray().map { it.toString().toInt() } }
        val tiled = tile(grid)
        val dist = dijkstra(tiled)

        println(dist)
        return dist
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)

    val input = readInput("Day15")
    println(part1(input))
    println(part2(input))
}
