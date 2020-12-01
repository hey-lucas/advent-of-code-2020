import java.io.File

fun solve(numbers: Array<Int>) : Int? {
    numbers.sort()

    val count = numbers.size

    for (i in 0..count) {
	var left = i + 1
	var right = count - 1


	while (left < right) {
	    val sum = numbers[left] + numbers[right] + numbers[i]

	    if (sum == 2020) {
		return numbers[left] * numbers[right] * numbers[i]
	    } else if (sum > 2020) {
		right--
	    } else {
		left++
	    }
	}
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

