import java.io.File


data class PasswordEntry(val firstOffset: Int, val secondOffset: Int, val letter: Char, val password: String) {
    companion object {
	val entryRegex = Regex("""(\d+)-(\d+) +([a-z]): ([a-z]+)""")
	
	fun of(string: String) : PasswordEntry? {
	    val matchResult = entryRegex.matchEntire(string)

	    if (matchResult != null) {
		val (firstIndex, secondIndex, letter, password) = matchResult.destructured
		return PasswordEntry(firstIndex.toInt() - 1, secondIndex.toInt() - 1, letter[0], password)
	    }

	    return null
	}
    }

    fun isValid(): Boolean {
	return (password[firstOffset] == letter).xor(password[secondOffset] == letter)
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
