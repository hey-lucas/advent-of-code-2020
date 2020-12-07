import java.io.File

class Graph() {
    val data: MutableMap<String, Set<String>> = mutableMapOf()

    fun addNodes(container: String, contained: Sequence<String>) {
	data.put(container, contained.toSet())
    }

    fun countPaths(destination: String) : Int = data.keys.count { it != destination && dfs(it, destination) }

    fun dfs(source: String, destination: String): Boolean {
	if (source == destination) {
	    return true
	}

	if (data.getOrElse(source, { setOf() }).any { dfs(it, destination) }) {
	    return true
	}

	return false
    }
}

fun File.toGraphOrNull(): Graph? {
    val sourceBagRegex = """([a-z ]+) bags contain""".toRegex()
    val destinationBagRegex = """\d\s([a-z ]+)\sbags?\s*(,|.)""".toRegex()
        
    val graph = Graph()

    forEachLine {
	val match = sourceBagRegex.find(it)
	if (match != null) {
	    val (sourceBag) = match.destructured

	    val destinationBags = destinationBagRegex.findAll(it).map {
		val (destinationBag) = it.destructured
		destinationBag
	    }

	    graph.addNodes(sourceBag, destinationBags)
	}
    }

    return graph
}

fun main() {
    print(
	File("input.txt").toGraphOrNull()?.countPaths("shiny gold")
    )
}

main()
