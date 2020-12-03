import java.io.File

data class Grid(val width: Int, val height: Int, val cells: Array<Array<Boolean>>) {
    fun isTree(row: Int, column: Int) = cells[row][column]

    fun countTrees() : Int {
	var currentRow = 0
	var currentColumn = 0

	val verticalStep = 1
	val horizontalStep = 3

	var trees = 0
	while (currentRow < height - 1) {
	    currentRow += verticalStep
	    currentColumn = (currentColumn + horizontalStep) % width // this ensures we wrap around

	    if (isTree(currentRow, currentColumn)) {
		trees++
	    }
	}

	return trees
    }
}

fun File.toGridOrNull(): Grid? {
    val cells =
	readLines().map { line -> line.map { it == '#' }.toTypedArray() }.toTypedArray()

    if (cells.size > 0 && cells[0].size > 0) {
	val height = cells.size
	val width = cells[0].size

	return Grid(width, height, cells)
    }

    return null
}

fun main() {
    val grid = File("input.txt").toGridOrNull()
    
    if (grid != null) {
	val steps = listOf(
	    Pair(1, 1),
	    Pair(1, 3),
	    Pair(1, 5),
	    Pair(1, 7),
	    Pair(2, 1),
	)
	
	val treeCounts = steps.map { (verticalStep, horizontalStep) -> grid.countTrees(verticalStep, horizontalStep) }
	val totalTrees = treeCounts.map { it.toBigInteger() }.reduce() { acc, n -> acc * n}

	println(totalTrees)
    }
}

main()
