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

fun makePrefixSum(array: Array<BigInteger>, start: Int, end: Int): Array<BigInteger> {
    val prefixSum = Array<BigInteger>(end - start + 1) { 0.toBigInteger() }

    for (i in 0..prefixSum.size-1) {
	if (i == 0) {
	    prefixSum[i] = array[start + i]
	} else {
	    prefixSum[i] = prefixSum[i-1]  + array[start + i]
	}
    }

    return prefixSum
}

fun subarraySum(array: Array<BigInteger>, start: Int, end: Int, target: BigInteger) {
    val prefixSum = makePrefixSum(array, start, end)
    var windowStart = 0
    var windowEnd = 0

    while (true) {
	var sum = prefixSum[windowEnd]
	if (windowStart != windowEnd) sum -= prefixSum[windowStart]

	if (sum > target) {
	    windowStart++
	} else if (sum < target) {
	    windowEnd++
	} else {
	    val subarray = array.slice(windowStart+1..windowEnd)
	    println(subarray)
	    val min = subarray.minOrNull()!!
	    val max = subarray.maxOrNull()!!
	    println(min)
	    println(max)
	    println(min + max)
	    return
	}
    }
}

fun main() {
    val preambleSize = 25
    val data = File("input.txt").readData()

    var preambleStart = 0

    for (i in preambleSize until data.size) {
	val preambleEnd = i - 1
	
	if (!isValid(data, preambleStart, preambleEnd, data[i])) {
	    println(data[i])
	    
	    subarraySum(data, 0, i-1, data[i])
	}

	preambleStart++
    }
}

main()
