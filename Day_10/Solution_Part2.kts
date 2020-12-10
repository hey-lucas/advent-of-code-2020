import java.io.File
import java.math.BigInteger

val INPUT_FILE_NAME = "test.txt"

// This was my initial implementation, though I knew it wouldn't work for the
// puzzle input. There are too many overlapping cases and we need to memoize the results.
// It works fine for the two small inputs.

/*
fun countCombinations(adapters: Set<Int>): BigInteger {
    val lastAdapterValue = adapters.maxOrNull()!!
    var combinations = 0.toBigInteger()
    
    fun backtrack(currentAdapterValue: Int) {
	if (currentAdapterValue == lastAdapterValue) combinations++

	for (i in 1..3) {
	    val nextAdapterValue = currentAdapterValue + i

	    if (nextAdapterValue in adapters) {
		backtrack(nextAdapterValue)
	    }
	}
    }

    backtrack(0)

    return combinations
}*/

fun countCombinations(adapters: IntArray): BigInteger {
    val lastAdapterValue = adapters.maxOrNull()!!
    val dp = mutableMapOf<Int, BigInteger>()
    dp.put(0, 1.toBigInteger())
    
    for (i in 0..adapters.size-1) {
	val adapter = adapters[i]
	
	for (diff in 1..3) {
	    dp[adapter] = dp.getOrDefault(adapter, 0.toBigInteger()) + dp.getOrDefault(adapter - diff, 0.toBigInteger())
	}
    }
    
    return dp.getOrDefault(lastAdapterValue, -1.toBigInteger())
}

fun main() {
    val sortedAdapters = File(INPUT_FILE_NAME).readLines().map { it.toInt() }.sorted().toMutableList()
    sortedAdapters.add(sortedAdapters.maxOrNull()!! + 3)

    val combinations = countCombinations(sortedAdapters.toIntArray())
    println(combinations)

}

main()
