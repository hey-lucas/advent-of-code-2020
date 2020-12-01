import java.io.File

fun solve(numbers: Array<Int>) : Int? {
    val complementMap = mutableMapOf<Int, Int>()

    for (index in numbers.indices) {
	val number: Int = numbers[index]

	if (complementMap.containsKey(number)) {
	    return number * complementMap.get(number)!!
	}

	val complement: Int = 2020 - number
	complementMap[complement] = number
    }

    return null
}

fun main() {
    val numbers = mutableListOf<Int>()
    
    File("input.txt").forEachLine { numbers.add(it.toInt()) }


    print(
	solve(numbers.toTypedArray())
    )
}

main()

