fun main() {
    fun part1(input: List<String>): Int {
        val outputs = input.map { it.split(" | ")[1].split(" ") }
        val outputLengths = outputs.flatMap { it.map { it.length } }
        return outputLengths.count {
            it == 2 || it == 4 || it == 3 || it == 7
        }
    }

    fun part2(input: List<String>): Int {
        var total = 0
        for (line in input) {
            val allSegments = line.split(" | ")[0].split(" ")
            val seven = mutableSetOf<Char>()
            seven.addAll(allSegments.find { it.length == 3 }!!.asIterable())
            val one = mutableSetOf<Char>()
            one.addAll(allSegments.find { it.length == 2}!!.asIterable())
            val a = seven subtract one
            val four = mutableSetOf<Char>()
            four.addAll(allSegments.find { it.length == 4 }!!.asIterable())
            val bd = four subtract one
            val fiveSegmentNumbers: List<Set<Char>> = allSegments.filter { it.length == 5 }.map {
                val result = mutableSetOf<Char>()
                result.addAll(it.asIterable())
                return@map result
            }
            var adg = fiveSegmentNumbers[0]
            for (i in 1 until fiveSegmentNumbers.size) {
                adg = adg intersect fiveSegmentNumbers[i]
            }

            val dg = adg subtract a
            val d = dg intersect bd
            val b = bd subtract d
            val g = dg subtract d

            val sixSegmentNumbers: List<Set<Char>> = allSegments.filter { it.length == 6 }.map {
                val result = mutableSetOf<Char>()
                result.addAll(it.asIterable())
                return@map result
            }

            var agbf = sixSegmentNumbers[0]
            for (i in 1 until sixSegmentNumbers.size) {
                agbf = agbf intersect sixSegmentNumbers[i]
            }

            val f = agbf subtract a subtract g subtract b
            val c = one subtract f

            val eight = mutableSetOf<Char>()
            eight.addAll(allSegments.find { it.length == 7 }!!.asIterable())

            val e = eight subtract a subtract b subtract c subtract d subtract f subtract g

            val two = a union c union d union e union g
            val three = a union c union d union f union g
            val five = a union b union d union f union g
            val six = a union b union d union e union f union g
            val nine = a union b union c union d union f union g
            val zero = a union b union c union e union f union g

            val numberMap = mapOf(
                zero to "0",
                one to "1",
                two to "2",
                three to "3",
                four to "4",
                five to "5",
                six to "6",
                seven to "7",
                eight to "8",
                nine to "9"
            )

            val outputs = line.split(" | ")[1].split(" ")
            var result = ""
            for (out in outputs) {
                result += numberMap[out.toSet()]
            }
            total += result.toInt()
        }

        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
