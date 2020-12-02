import java.io.File


data class PasswordEntry(val min: Int, val max: Int, val letter: Char, val password: String) {
    companion object {
	val entryRegex = Regex("""(\d+)-(\d+) +([a-z]): ([a-z]+)""")
	
	fun of(string: String) : PasswordEntry? {
	    val matchResult = entryRegex.matchEntire(string)

	    if (matchResult != null) {
		val (min, max, letter, password) = matchResult.destructured
		return PasswordEntry(min.toInt(), max.toInt(), letter[0], password)
	    }

	    return null
	}
    }

    fun isValid(): Boolean {
	val count = password.count({ it == letter })
	return (min <= count && count <= max)
    }
}

fun main() {
    var numberOfValidPasswords: Int = 0

    File("input.txt").forEachLine {
	val entry = PasswordEntry.of(it)

	if (entry != null && entry.isValid()) {
	    numberOfValidPasswords++
	}
    }

    println(numberOfValidPasswords)
}

main()
