import java.io.File

data class Passport(
    val byr: Int?,
    val iyr: Int?,
    val eyr: Int?,
    val hgt: String?,
    val hcl: String?,
    val ecl: String?,
    val pid: String?,
    val cid: String?) {

    class Builder {
	var byr: Int? = null
	var iyr: Int? = null
	var eyr: Int? = null
	var hgt: String? = null
	var hcl: String? = null
	var ecl: String? = null
	var pid: String? = null
	var cid: String? = null

	fun setField(fieldName: String, fieldValue: String) {
	    when (fieldName) {
		"byr" -> byr = fieldValue.toInt()
		"iyr" -> iyr = fieldValue.toInt()
		"eyr" -> eyr = fieldValue.toInt()
		"hgt" -> hgt = fieldValue
		"hcl" -> hcl = fieldValue
		"ecl" -> ecl = fieldValue
		"pid" -> pid = fieldValue
		"cid" -> cid = fieldValue
	    }

	}

	fun build() = Passport(
	    byr, iyr, eyr,
	    hgt, hcl, ecl,
	    pid, cid)
    }

    val validEyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val hairColorRegex = Regex("""#[a-f0-9]{6}""")

    fun validHeight(height: String): Boolean {
	if (height.contains("in")) {
	    val value = height.substring(0..height.length - 3).toInt()
	    return 50 <= value && value <= 76
	} else if (height.contains("cm")) {
	    val value = height.substring(0..height.length - 3).toInt()
	    return 150 <= value && value <= 193
	} else {
	    return false
	}
    }
    
    fun isValid(): Boolean {
	return (
	    byr != null && 1920 <= byr && byr <= 2002 &&
	    iyr != null && 2010 <= iyr && iyr <= 2020 &&
	    eyr != null && 2020 <= eyr && eyr <= 2030 &&
	    hgt != null && validHeight(hgt) &&
	    hcl != null && hairColorRegex.matches(hcl) &&
	    ecl != null && validEyeColors.contains(ecl) &&
	    pid != null && pid.length == 9
	)
    }
}

fun File.toPassportListOrNull() : List<Passport>? {
    val result = ArrayList<Passport>()
    var passportBuilder = Passport.Builder()
    
    forEachLine {
	if (it.isEmpty()) {
	    result.add(passportBuilder.build())
	    passportBuilder = Passport.Builder()
	} else {
	    it.split(" ").forEach {
		val parts = it.split(":")
		passportBuilder.setField(parts[0], parts[1])
	    }
	}
    }

    result.add(passportBuilder.build())

    return result
}

fun main() {
    val count = File("input.txt").toPassportListOrNull()?.count { it.isValid() }

    print(count)
}

main()
