import java.io.File

fun main() {

    var groupAnswers = mutableSetOf<Char>()
    var count = 0
    var firstTime = true
 
    File("input.txt").readLines().forEach {
	if (it.isEmpty()) {
	    count += groupAnswers.size
	    groupAnswers.clear()
	    firstTime = true
	} else {
	    if (firstTime) {
		firstTime = false
		groupAnswers.addAll(it.toList())		
	    } else {
		groupAnswers = groupAnswers.intersect(it.toList()).toMutableSet()
	    }
	}
    }

    count += groupAnswers.size
    println(count)
}

main()
