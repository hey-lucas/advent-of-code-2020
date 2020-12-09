import java.io.File

sealed class Instruction {
    data class Acc(val immediate: Int) : Instruction()
    data class Jmp(val offset: Int) : Instruction()
    class Nop() : Instruction()
}

class Console(val rom: Array<Instruction>) {
    var pc: Int = 0
    var accumulator: Int = 0

    fun run() {
	val seenInstructions = ByteArray(rom.size) { 0 }
	val seen = 1.toByte()
	
	while (true) {
	    if (seenInstructions[pc] == seen) {
		break
	    }

	    seenInstructions[pc] = seen
	    val instr = rom[pc]

	    when (instr) {
		is Instruction.Acc -> {
		    accumulator += instr.immediate
		    pc++
		}
		is Instruction.Jmp ->
		    pc += instr.offset
		is Instruction.Nop ->
		    pc++
	    }
	}

	println(accumulator)
    }
}


fun String.toInstructionOrNull() : Instruction? {
    if (isEmpty()) return null

    val parts = split(" ")

    when (parts[0]) {
	"nop" -> return Instruction.Nop()
	"acc" -> return Instruction.Acc(parts[1].toInt())
	"jmp" -> return Instruction.Jmp(parts[1].toInt())
	else -> return null
    }
}

fun File.toInstructionsArray() : Array<Instruction> {
    val instructions = mutableListOf<Instruction>()
    
    forEachLine {
	val instr = it.toInstructionOrNull()

	if (instr != null) {
	    instructions.add(instr)
	}
    }

    return instructions.toTypedArray()
}

fun main() {
    val rom = File("input.txt").toInstructionsArray()
    Console(rom).run()
}

main()
