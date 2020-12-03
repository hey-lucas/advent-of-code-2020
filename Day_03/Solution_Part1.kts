import java.io.File

data class Grid(val width: Int, val height: Int, val cells: Array<Array<Boolean>>) {
    companion object {
	fun fromFile(file: File) : Grid? {
	    val cells =
		file.readLines().map { line -> line.map { it == '#' }.toTypedArray() }.toTypedArray()

	    if (cells.size > 0 && cells[0].size > 0) {
		val height = cells.size
		val width = cells[0].size

		return Grid(width, height, cells)
	    }

	    return null
	}
    }

    fun isTree(row: Int, column: Int) = cells[row][column]
}

fun solve(grid: Grid) : Int {
    var currentRow = 0
    var currentColumn = 0

    val verticalStep = 1
    val horizontalStep = 3

    var trees = 0
    while (currentRow < grid.height - 1) {
	currentRow += verticalStep
	currentColumn = (currentColumn + horizontalStep) % grid.width // this ensures we wrap around

	if (grid.isTree(currentRow, currentColumn)) {
	    trees++
	}
    }

    return trees
}

fun main() {
    val grid = Grid.fromFile(File("input.txt"))

    if (grid != null) {
	println(solve(grid))
    }
}

main()
