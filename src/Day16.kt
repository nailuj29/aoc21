abstract class Packet(val version: Int) {
    abstract fun value(): Long
}

class Operator(val packets: MutableList<Packet>, version: Int, private val type: Int) : Packet(version) {
    override fun value(): Long {
        return when (type) {
            0 -> packets.sumOf { it.value() }
            1 -> packets.map { it.value() }.reduce { a, b -> a * b }
            2 -> packets.minOf { it.value() }
            3 -> packets.maxOf { it.value() }
            5 -> if (packets[0].value() > packets[1].value()) 1 else 0
            6 -> if (packets[0].value() < packets[1].value()) 1 else 0
            7 -> if (packets[0].value() == packets[1].value()) 1 else 0
            else -> -1
        }
    }
}

class Literal(val value: Long, version: Int) : Packet(version) {
    override fun value(): Long {
        return value
    }
}

fun main() {
    fun parsePacket(packet: String, start: Int = 0): Pair<Packet, Int> {
        val binary = packet
            .replace("0", "0000")
            .replace("1", "0001")
            .replace("2", "0010")
            .replace("3", "0011")
            .replace("4", "0100")
            .replace("5", "0101")
            .replace("6", "0110")
            .replace("7", "0111")
            .replace("8", "1000")
            .replace("9", "1001")
            .replace("A", "1010")
            .replace("B", "1011")
            .replace("C", "1100")
            .replace("D", "1101")
            .replace("E", "1110")
            .replace("F", "1111")

        var pos = start

        val version = binary.substring(pos, pos + 3).toInt(2)
        pos += 3
        val type = binary.substring(pos, pos + 3).toInt(2)
        pos += 3
        when (type) {
            4 -> {
                var string = binary.substring(pos, pos + 5)
                var final = string.substring(1)
                pos += 5
                while (!string.startsWith("0")) {
                    string = binary.substring(pos, pos + 5)
                    final += string.substring(1)
                    pos += 5
                }
                return Pair(Literal(final.toLong(2), version), pos)
            }
            else -> {
                val typeId = binary.substring(pos, pos + 1)
                pos += 1
                val packetOp = Operator(mutableListOf(), version, type)
                if (typeId == "0") {
                    val length = binary.substring(pos, pos + 15).toInt(2)
                    pos += 15
                    val finalPos = pos + length
                    while (pos < finalPos) {
                        val packetParsed = parsePacket(packet, pos)
                        packetOp.packets.add(packetParsed.first)
                        pos = packetParsed.second
                    }
                } else {
                    val count = binary.substring(pos, pos + 11).toInt(2)
                    pos += 11
                    for (i in 1..count) {
                        val packetParsed = parsePacket(packet, pos)
                        packetOp.packets.add(packetParsed.first)
                        pos = packetParsed.second
                    }
                }
                return Pair(packetOp, pos)
            }
        }
    }

    fun part1(input: List<String>): Int {
        fun totalVersions(packet: Packet): Int {
            if (packet is Operator) {
                return packet.packets.sumOf { totalVersions(it) } + packet.version
            }

            return packet.version
        }
        val parsed = parsePacket(input[0])

        return totalVersions(parsed.first)
    }

    fun part2(input: List<String>): Long {
        val parsed = parsePacket(input[0])

        return parsed.first.value()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 31)
    val testInput2 = readInput("Day16_test2")
    check(part2(testInput2) == 1L)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
