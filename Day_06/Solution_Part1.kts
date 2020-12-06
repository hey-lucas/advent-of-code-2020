import java.io.File

fun main() {

    var groupAnswers = mutableSetOf<Char>()
    var count = 0
    File("input.txt").readLines().forEach {
	if (it.isEmpty()) {
	    count += groupAnswers.size
	    groupAnswers.clear()
	} else {
	    groupAnswers.addAll(it.toList())
	}
    }

    count += groupAnswers.size
    print(count)
}

main()
