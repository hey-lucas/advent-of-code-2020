import java.io.File
import java.io.BufferedReader
import java.math.BigInteger

fun File.readData(): Array<BigInteger> =
    readLines().map { it.toBigInteger() }.toTypedArray()

fun isValid(array: Array<BigInteger>, start: Int, end: Int, target: BigInteger) : Boolean {
    val complementMap = mutableMapOf<BigInteger, BigInteger>()

    for (i in start..end) {
	val number = array[i]

	if (complementMap.containsKey(number)) {
	    return true
	}

	val complement: BigInteger = target - number
	complementMap[complement] = number
    }

    return false
}

fun main() {
    val preambleSize = 25
    val data = File("input.txt").readData()

    var preambleStart = 0

    for (i in preambleSize until data.size) {
	val preambleEnd = i - 1
	
	if (!isValid(data, preambleStart, preambleEnd, data[i])) {
	    println(data[i])
	    break
	}

	preambleStart++
    }
}

main()
