enum class CaveType {
    Start,
    End,
    Small,
    Large
}

data class Cave(val name: String, val type: CaveType, val connections: MutableList<Cave>)

fun main() {
    fun parse(input: List<String>): Cave {
        fun determineType(name: String): CaveType {
            val lowercase = "^[a-z]+$".toRegex()
            val uppercase = "^[A-Z]+$".toRegex()

            if (name == "start") {
                return CaveType.Start
            }

            if (name == "end") {
                return CaveType.End
            }

            if (name.matches(lowercase)) {
                return CaveType.Small
            }

            if (name.matches(uppercase)) {
                return CaveType.Large
            }

            return CaveType.End
        }

        val caves = mutableMapOf<String, Cave>()

        for (line in input) {
            val split = line.split("-")
            if (caves.containsKey(split[0]) && caves.containsKey(split[1])) {
                caves[split[0]]!!.connections.add(caves[split[1]]!!)
                caves[split[1]]!!.connections.add(caves[split[0]]!!)
            } else if (caves.containsKey(split[0])) {
                val newCave = Cave(split[1], determineType(split[1]), mutableListOf())
                caves[split[1]] = newCave
                caves[split[0]]!!.connections.add(caves[split[1]]!!)
                caves[split[1]]!!.connections.add(caves[split[0]]!!)
            } else if (caves.containsKey(split[1])) {
                val newCave = Cave(split[0], determineType(split[0]), mutableListOf())
                caves[split[0]] = newCave
                caves[split[0]]!!.connections.add(caves[split[1]]!!)
                caves[split[1]]!!.connections.add(caves[split[0]]!!)
            } else {
                val newCave1 = Cave(split[0], determineType(split[0]), mutableListOf())
                caves[split[0]] = newCave1
                val newCave2 = Cave(split[1], determineType(split[1]), mutableListOf())
                caves[split[1]] = newCave2
                caves[split[0]]!!.connections.add(caves[split[1]]!!)
                caves[split[1]]!!.connections.add(caves[split[0]]!!)
            }
        }

        return caves["start"]!!
    }

    fun part1(input: List<String>): Int {
        fun traverse(cave: Cave): Int {
            var count = 0
            fun recurs(cave: Cave, visited: List<String>) {
                if (cave.type == CaveType.End) {
                    count++
                    return
                }

                if ((cave.type == CaveType.Small || cave.type == CaveType.Start) && visited.contains(cave.name)) {
                    return
                }

                for (conn in cave.connections) {
                    recurs(conn, visited + cave.name)
                }
            }
            recurs(cave, listOf())
            return count
        }

        val cave = parse(input)

        return traverse(cave)
    }

    fun part2(input: List<String>): Int {
        fun traverse(cave: Cave): Int {
            var count = 0
            fun recurs(cave: Cave, visited: List<String>, repeated: Boolean) {
                if (cave.type == CaveType.End) {
                    count++
                    return
                }

                if (cave.type == CaveType.Start && visited.contains(cave.name)) {
                    return
                }

                var nextRepeated = repeated
                if (cave.type == CaveType.Small && visited.contains(cave.name)) {
                    if (repeated) {
                        return
                    } else {
                        nextRepeated = true
                    }
                }

                for (conn in cave.connections) {
                    recurs(conn, visited + cave.name, nextRepeated)
                }
            }
            recurs(cave, listOf(), false)
            return count
        }

        val cave = parse(input)

        return traverse(cave)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
