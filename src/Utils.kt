import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun List<Int>.getOrMax(index: Int): Int {
    return try {
        this[index]
    } catch (e: IndexOutOfBoundsException) {
        Int.MAX_VALUE
    }
}

fun List<List<Int>>.getOrEmpty(index: Int): List<Int> {
    return try {
        this[index]
    } catch (e: IndexOutOfBoundsException) {
        listOf()
    }
}