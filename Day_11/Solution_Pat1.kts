import java.io.File

enum class Seat {
    Empty,
    Occupied,
    Floor
}

fun File.readSeats(): Array<Array<Seat>> {
    return readLines().map {
	line -> line.map {
	    when (it) {
		'.' -> Seat.Floor
		'L' -> Seat.Empty
		else -> Seat.Occupied
	    }
	}.toTypedArray()
    }.toTypedArray()
}

fun printSeats(seats: Array<Array<Seat>>) {
    for (row in seats) {
	for (seat in row) {
	    print(when (seat) {
		Seat.Empty -> 'L'
		Seat.Occupied -> '#'
		Seat.Floor -> '.'
	    })
	}
	println("")
    }

    println("")
}

fun countOccupiedNeighbors(seats: Array<Array<Seat>>, row: Int, column: Int): Int {
    var count = 0
    for (rowOffset in -1..1) {
	for (colOffset in -1..1) {
	    val rr = row + rowOffset
	    val cc = column + colOffset

	    // bounds check
	    if ((rr == row && cc == column) ||
		 (rr < 0 || rr >= seats.size) ||
		 (cc < 0 || cc >= seats[0].size)) {
		continue
	    }

	    if (seats[rr][cc] == Seat.Occupied) count++
	}
    }

    return count
}

fun countOccupiedSeats(seats: Array<Array<Seat>>): Int =
    seats.map { it.count { it == Seat.Occupied }}.reduce { acc, n -> acc + n }

fun simulate(seats: Array<Array<Seat>>): Int {
    val rows = seats.size
    val cols = seats[0].size

    var current = seats
    var next = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}

    while (true) {
	var changed = false	
//	printSeats(current)
	for (i in 0..rows-1) {
	    for (j in 0..cols-1) {
		val numOccupiedNeighbors = countOccupiedNeighbors(current, i, j)

		if (current[i][j] == Seat.Empty && numOccupiedNeighbors == 0) {
		    next[i][j] = Seat.Occupied
		    changed = true
		} else if (current[i][j] == Seat.Occupied && numOccupiedNeighbors >= 4) {
		    next[i][j] = Seat.Empty
		    changed = true
		} else {
		    next[i][j] = current[i][j]
		}
	    }
	}

	val tmp = current
	current = next
	next = tmp

	if (!changed) {
	    break
	}
    }

    return countOccupiedSeats(current)

}

fun main() {
    val seats = File("small_input2.txt").readSeats()
    println(simulate(seats))
}

main()
