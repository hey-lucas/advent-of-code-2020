import java.io.File

val INPUT_FILE_NAME = "test.txt"

fun main() {
    val sortedAdapters = File(INPUT_FILE_NAME).readLines().map { it.toInt() }.sorted().toMutableList()
    sortedAdapters.add(0, 0)
    sortedAdapters.add(sortedAdapters.maxOrNull()!! + 3)

    val adapters = sortedAdapters.toIntArray()

    var oneDiffCount = 0
    var threeDiffCount = 0
    for (i in 1..adapters.size-1) {
	val diff = adapters[i] - adapters[i-1]

	if (diff == 1) oneDiffCount++
	if (diff == 3) threeDiffCount++
    }

    println(oneDiffCount * threeDiffCount)
}

main()
