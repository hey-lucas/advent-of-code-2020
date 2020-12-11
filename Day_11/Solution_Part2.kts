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

fun countOccupiedSeats(seats: Array<Array<Seat>>): Int =
    seats.map { it.count { it == Seat.Occupied }}.reduce { acc, n -> acc + n }

fun simulate(seats: Array<Array<Seat>>): Int {
    val rows = seats.size
    val cols = seats[0].size

    var current = seats
    var next = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}

    var left = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}
    var right = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}
    var top = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}
    var bottom = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}
    var leftDiagonalBottom = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}
    var leftDiagonalTop = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}    
    var rightDiagonalBottom = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}
    var rightDiagonalTop = Array<Array<Seat>>(rows) { Array<Seat>(cols) {Seat.Floor}}        
	
    while (true) {
	for (r in 0..rows-1) {
	    for (c in 0..cols-1) {
		// Fill closest to the top		
		if (r == 0) {
		    top[r][c] = Seat.Empty
		} else if (current[r-1][c] == Seat.Floor) {
		    top[r][c] = top[r-1][c]
		} else {
		    top[r][c] = current[r-1][c]
		}

		// Fill the closest to the right diagonal top
		if (c == 0 || r == 0) {
		    rightDiagonalTop[r][c] = Seat.Empty
		} else if (current[r-1][c-1] == Seat.Floor) {
		    rightDiagonalTop[r][c] = rightDiagonalTop[r-1][c-1]
		} else {
		    rightDiagonalTop[r][c] = current[r-1][c-1]
		}		
	    }

	    for (c in cols-1 downTo 0) {
		// Fill the closest to the right diagonal top
		if (c == cols-1 || r == 0) {
		    leftDiagonalTop[r][c] = Seat.Empty
		} else if (current[r-1][c+1] == Seat.Floor) {
		    leftDiagonalTop[r][c] = leftDiagonalTop[r-1][c+1]
		} else {
		    leftDiagonalTop[r][c] = current[r-1][c+1]
		}			
	    }
	}
	
	for (r in rows-1 downTo 0) {
	    for (c in 0..cols-1) {
		// Fill closest to the bottom
		if (r == rows-1) {
		    bottom[r][c] = Seat.Empty
		} else if (current[r+1][c] == Seat.Floor) {
		    bottom[r][c] = bottom[r+1][c]
		} else {
		    bottom[r][c] = current[r+1][c]		    
		}

		// Fill the closest to the left
		if (c == 0) {
		    left[r][c] = Seat.Empty
		} else if(current[r][c-1] == Seat.Floor) {
		    left[r][c] = left[r][c-1]
		} else {
		    left[r][c] = current[r][c-1]
		}

		// Fill the closest to the left diagonal
		if (c == 0 || r == rows-1) {
		    leftDiagonalBottom[r][c] = Seat.Empty
		} else if (current[r+1][c-1] == Seat.Floor) {
		    leftDiagonalBottom[r][c] = leftDiagonalBottom[r+1][c-1]
		} else {
		    leftDiagonalBottom[r][c] = current[r+1][c-1]
		}
	    }

	    for (c in cols-1 downTo 0) {
		// Fill closest to the right		
		if (c == cols-1) {
		    right[r][c] = Seat.Empty
		} else if (current[r][c+1] == Seat.Floor) {
		    right[r][c] = right[r][c+1]
		} else {
		    right[r][c] = current[r][c+1]
		}

		// Fill closest to the right diagonal
		if (c == cols-1 || r == rows-1) {
		    rightDiagonalBottom[r][c] = Seat.Empty
		} else if (current[r+1][c+1] == Seat.Floor) {
		    rightDiagonalBottom[r][c] = rightDiagonalBottom[r+1][c+1]
		} else {
		    rightDiagonalBottom[r][c] = current[r+1][c+1]
		}		
	    }
	}

	var changed = false
	for(i in 0..rows-1) {
	    for (j in 0..cols-1) {
		var numOccupiedInSight = 0
		
		if(left[i][j] == Seat.Occupied) numOccupiedInSight++
		if(right[i][j] == Seat.Occupied) numOccupiedInSight++
		if(top[i][j] == Seat.Occupied) numOccupiedInSight++
		if(bottom[i][j] == Seat.Occupied) numOccupiedInSight++
		if(leftDiagonalBottom[i][j] == Seat.Occupied) numOccupiedInSight++
		if(leftDiagonalTop[i][j] == Seat.Occupied) numOccupiedInSight++
		if(rightDiagonalBottom[i][j] == Seat.Occupied) numOccupiedInSight++
		if(rightDiagonalTop[i][j] == Seat.Occupied) numOccupiedInSight++

		if (current[i][j] == Seat.Empty && numOccupiedInSight == 0) {
		    next[i][j] = Seat.Occupied
		    changed = true
		} else if (current[i][j] == Seat.Occupied && numOccupiedInSight >= 5) {
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
    val seats = File("input.txt").readSeats()
    println(simulate(seats))
}

main()
