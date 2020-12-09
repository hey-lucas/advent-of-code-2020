import java.io.File

sealed class Instruction {
    data class Acc(val immediate: Int) : Instruction()
    data class Jmp(val immediate: Int) : Instruction()
    data class Nop(val immediate: Int) : Instruction()
}

class Console(val rom: Array<Instruction>) {
    var pc: Int = 0
    var accumulator: Int = 0

    fun run() : Boolean {
	pc = 0
	accumulator = 0
	
	val seenInstructions = ByteArray(rom.size) { 0 }
	val seen = 1.toByte()
	
	while (true) {
	    if (pc == rom.size) {
		return true
	    }
	    
	    if (seenInstructions[pc] == seen) {
		return false
	    }

	    seenInstructions[pc] = seen
	    val instr = rom[pc]

	    when (instr) {
		is Instruction.Acc -> {
		    accumulator += instr.immediate
		    pc++
		}
		is Instruction.Jmp ->
		    pc += instr.immediate
		is Instruction.Nop ->
		    pc++
	    }
	}
    }
}

fun String.toInstructionOrNull() : Instruction? {
    if (isEmpty()) return null

    val parts = split(" ")

    when (parts[0]) {
	"nop" -> return Instruction.Nop(parts[1].toInt())
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
    val console = Console(rom)

    for (i in 0 until rom.size) {
	var instr = rom[i]

	when (instr) {
	    is Instruction.Nop ->		{
		rom[i] = Instruction.Jmp(instr.immediate)
		if (console.run()) println(console.accumulator)
		rom[i] = Instruction.Nop(instr.immediate)
	    }
	    is Instruction.Jmp -> {
		rom[i] = Instruction.Nop(instr.immediate)
		if (console.run()) println(console.accumulator)
		rom[i] = Instruction.Jmp(instr.immediate)	    
	    }
	    else -> {}
	}
    }
}

main()
