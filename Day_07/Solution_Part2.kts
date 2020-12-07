import java.io.File

class Graph() {
    val data: MutableMap<String, Set<Pair<String, Int>>> = mutableMapOf()

    fun addNodes(container: String, contained: Sequence<Pair<String, Int>>) {
	data.put(container, contained.toSet())
    }

    fun countBags(source: String): Int {
	var total = 0
	for ((bag, bagCount) in data.getOrElse(source, { setOf<Pair<String, Int>>() })) {
	    total += bagCount * countBags(bag)
	}

	return total + 1
    }
}

fun File.toGraphOrNull(): Graph? {
    val sourceBagRegex = """([a-z ]+) bags contain""".toRegex()
    val destinationBagRegex = """(\d+)\s([a-z ]+)\sbags?\s*(,|.)""".toRegex()
        
    val graph = Graph()

    forEachLine {
	val match = sourceBagRegex.find(it)
	if (match != null) {
	    val (sourceBag) = match.destructured

	    val destinationBags = destinationBagRegex.findAll(it).map {
		val (count, destinationBag) = it.destructured
		Pair(destinationBag, count.toInt())
	    }

	    graph.addNodes(sourceBag, destinationBags)
	}
    }

    return graph
}

fun main() {
    print(
	File("input.txt").toGraphOrNull()?.countBags("shiny gold")!! - 1
    )
}

main()
