import java.io.File

data class BoardingPass(val row: Int, val column: Int) {
    val seatID : Int
        get() = row * 8 + column
}

fun String.toBoardingPass(): BoardingPass {
    // Find the correct column
    var lo = 0
    var hi = 127
    for (i in 0..6) {
	val mid = lo + (hi - lo) / 2

	if (this[i] == 'F') {
	    hi = mid
	} else {
	    lo = mid + 1
	}
    }

    val row = lo

    // Find the correct row
    lo = 0
    hi = 7
    for (i in 7..9) {
	val mid = lo + (hi - lo)  / 2
	if (this[i] == 'L') {
	    hi = mid
	} else {
	    lo = mid + 1
	}
    }

    val column = lo
    
    return BoardingPass(row, column)
}

fun File.toListOfBoardingPassesOrNull() : List<BoardingPass>? {
    return readLines().map { it.toBoardingPass() }
}

fun main() {
    val totalSeats = 128 * 8
    val maxSeatID = totalSeats - 1
    
    // Preallocate array for seats. Zero indicates the seat is emtpy
    val seats = ByteArray(totalSeats) { 0 }
    
    File("input.txt").toListOfBoardingPassesOrNull()?.map { it.seatID }?.forEach {
	seats[it] = 1
    }

    for (i in 1..maxSeatID-1) {
	// The current seat is empty but the previous and next seats are taken,
	// so that's our seat.
	if (seats[i] == 0.toByte() && seats[i-1] == 1.toByte() && seats[i + 1] == 1.toByte()) {
	    print(i)
	}
    }
}

main()
