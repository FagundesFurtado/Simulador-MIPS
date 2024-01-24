package controller

import entity.Type
import model.Register
import model.Word
import view.InputUI
import javax.swing.JOptionPane

class Processor(
    private val instructions: ArrayList<String>,
    private val m: MipsSimulator,
) {
    private var pc: Int = 0
    private var clock: Int = 0
    var forwarding: Boolean = false
    private var registers: ArrayList<Register> = ArrayList()
    private var decodSearch: Word
    private var decodUla: Word
    private var ulaMemory: Word
    private var writeBackMemory: Word
    private var writeBack: Word
    private var searchingWord: Word
    var memory: Array<String?> = arrayOfNulls(1024)
    var binaryInstructions: ArrayList<StringBuilder> = ArrayList()
    private var busca = true
    private var decod = true
    private var jump = false

    init {
        for (i in 0..33) {
            registers.add(Register())
        }

        instructions.add("end")
        instructions.add("end")
        instructions.add("end")
        instructions.add("end")
        instructions.add("end")
        searchingWord = Word("noop")
        decodSearch = Word("noop")
        decodUla = Word("noop")
        ulaMemory = Word("noop")
        writeBackMemory = Word("noop")
        writeBack = Word("noop")
    }

    private fun checkBusyRegisters() {
        val lock = ArrayList<Int>()

        for (i in 0 until registers.size) {
            if (registers.get(i).isBusy) {
                lock.add(i)
            }
        }

        println(lock.toString())
    }

    fun geraBinario() {
        for (i in instructions.indices) {
            var teste = instructions[i]
            teste = teste.replaceFirst(" ".toRegex(), ",")
            teste = teste.replace(" ".toRegex(), "")
            val aux = teste.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if (aux[0] == "add" || aux[0] == "sub" || aux[0] == "mult" || aux[0] == "div") {
                handleTypeRBinary(aux)

                // tipo R
            } else {
                // TIPO I
                if (aux[0] == "bne" || aux[0] == "beq" || aux[0] == "lw" || aux[0] == "sw" || aux[0] == "bltz" || aux[0] == "bgtz") {
                    handleTypeIBinary(aux)
                } else {
                    // Tipo J
                    if (aux[0] == "j" || aux[0] == "jr") {
                        handleTypeJ(aux)
                    } else {
                        // Tipo D
                        if (aux[0] == "noop" || aux[0] == "get_tc") {
                            handleTypeDBinary(aux)
                        }
                    }
                }
            }

            for (j in aux.indices) {
                print(" " + aux[j])
            }
            println("")
        }
        m.storeInDisk()
        for (i in binaryInstructions) {
            println(i)
        }
    }

    fun nextOperation(): ArrayList<String> {
        val aux = ArrayList<String>()
        if (pc == instructions.size - 1) {
            JOptionPane.showMessageDialog(null, "Fim de execução")
        } else {
            writeBack()
            handleMemory()
            ula()
            if (!jump) {
                handleDecodification()
                searchForInstruction()
            } else {
                pc--
            }

            unlockRegisters(writeBack)
            checkBusyRegisters()

            jump = false

            if (busca) {
                pc++
            }
            clock++

            aux.add(decodSearch.word)
            aux.add(decodUla.word)
            aux.add(ulaMemory.word)
            aux.add(writeBackMemory.word)
            aux.add(writeBack.word)
            aux.add(pc.toString())
            aux.add(clock.toString())
            aux.add(forwarding.toString())
            aux.add(reg())
        }
        return aux
    }

    private fun unlockRegisters(word: Word) {
        registers[word.r1].isBusy = false
        registers[word.r1].isBusy = false
        registers[word.r1].isBusy = false
    }

    private fun reg(): String {
        var registers = ""

        registers += "\n"
        for (i in 0 until this.registers.size) {
            registers = registers + "  " + this.registers[i].value
        }

        return registers
    }

    private fun searchForInstruction() {
        if (forwarding) {
            decodSearch.word = instructions[pc].replaceFirst(" ".toRegex(), ",")
            decodSearch.word = decodSearch.word.replace(" ", "")
        } else {
            if (busca && decodSearch.word == "noop") {
                decodSearch.word = instructions[pc].replaceFirst(" ".toRegex(), ",")
                decodSearch.word = decodSearch.word.replace(" ", "")
            } else {
                if (busca) {
                    decodSearch.word = instructions[pc].replaceFirst(" ".toRegex(), ",")
                    decodSearch.word = decodSearch.word.replace(" ", "")
                }
            }
        }

        // System.out.println("Busca " + busca_decod.word);
    }

    private fun handleDecodification() {
        if (forwarding) {
            codeDecod()
        } else {
            if (decod && decodSearch.word != "noop") {
                if (decodSearch.word != "end") {
                    codeDecod()
                } else {
                    decodUla.word = "end"
                }
            }
        }
    }

    private fun codeDecod() {
        val aux: List<String> = decodSearch.word.split(",")
        if (aux[0] == "add" || aux[0] == "sub" || aux[0] == "mult" || aux[0] == "div") {
            decodSearch.type = Type.R
            // Integer.parseInt(aux[1].substring(1, aux[1].length()));
            decodSearch.r1 = (aux[1].substring(1, aux[1].length).toInt())
            decodSearch.r2 = (aux[2].substring(1, aux[2].length).toInt())
            decodSearch.r3 = (aux[3].substring(1, aux[3].length).toInt())
            decodSearch.op = (aux[0])

            //  handleTypeRBinary(busca_decod);
        } else {
            if (aux[0] == "lw" || aux[0] == "sw") {
                decodSearch.type = Type.I
                decodSearch.op = (aux[0])
                decodSearch.r1 = (aux[1].substring(1, aux[1].length).toInt())

                val aux2 = aux[2].split("\\(".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                decodSearch.r2 = (aux2[1].substring(1, aux2[1].length - 1).toInt())
                decodSearch.offset = (aux2[0].replace(" ".toRegex(), "").toInt())
            } else {
                if (aux[0] == "bne" || aux[0] == "beq" || aux[0] == "bltz" || aux[0] == "bgtz") {
                    decodSearch.type = Type.I
                    decodSearch.op = (aux[0])
                    decodSearch.r1 = (aux[1].substring(1, aux[1].length).toInt())
                    decodSearch.r2 = (aux[2].substring(1, aux[2].length).toInt())

                    decodSearch.offset = (aux[3].toInt())
                } else {
                    if (aux[0] == "jr" || aux[0] == "j") {
                        decodSearch.type = Type.J
                        decodSearch.jump = (aux[1].toInt())
                        decodSearch.op = (aux[0])
                    } else {
                        if (aux[0] == "get_tc") {
                            decodSearch.type = Type.D
                            decodSearch.temp = (aux[1].toInt())
                        }
                    }
                }
            }
        }
        decodUla = decodSearch
        decodSearch = Word("noop")
    }

    private fun lockRegister(word: Word) {
        registers.get(word.r1).isBusy = (true)
        registers.get(word.r2).isBusy = (true)
        registers.get(word.r3).isBusy = (true)
    }

    private fun ula() {
        if (!decodUla.word.equals("end")) {
            if (forwarding) {
                codeULA()
            } else {
                if (!registers[decodUla.r1].isBusy &&
                    !registers[decodUla.r2].isBusy &&
                    !registers[decodUla.r3].isBusy
                ) {
                    lockRegister(decodUla)
                    codeULA()
                    busca = true
                    decod = true
                } else {
                    busca = false
                    decod = false
                }
            }
        } else {
            ulaMemory.word = ("end")
        }
    }

    private fun forwardingOn(word: Word) {
        when (word.type) {
            Type.I -> {
                if (word.op == "sw") {
                    sw(word)
                }
                if (word.op == "lw") {
                    lw(word)
                }
            }

            Type.R -> {
                if (word.word != "noop" && word.word != "end") {
                    handleTypeR(word)
                }
            }
            else -> Unit
        }
    }

    private fun codeULA() {
        if (decodUla.word != "noop") {
            when (decodUla.type) {
                Type.I -> {
                    handleTypeI(decodUla)
                    if (forwarding) {
                        forwardingOn(decodUla)
                    }
                }

                Type.J -> handleTypeJ(decodUla)
                Type.R ->
                    if (forwarding) {
                        handleTypeR(decodUla)
                    }

                Type.D -> handleTypeD(decodUla)
            }
            ulaMemory = decodUla
            //  System.out.println("ULA " + ula_memoria.word);
            decodUla = Word("noop")
        }
    }

    private fun handleTypeD(word: Word) {
        val c = InputUI(this, word.temp)
        c.isVisible = true
    }

    private fun handleMemory() {
        // lw ou sw

        if (ulaMemory.type == Type.I) {
            unlockRegisters(decodSearch)
            if (ulaMemory.op == "sw") {
                sw(ulaMemory)
            }
        }

        writeBackMemory = ulaMemory
        //  System.out.println("memory " + ula_memoria.word);
        ulaMemory = Word("noop")
    }

    private fun writeBack() {
        // add, sub,  mult ou div
        //  System.out.println("WriteBack: " + memoria_writeback.word);
        if (writeBackMemory.type == Type.R) {
            if (writeBackMemory.word != "noop" && writeBackMemory.word != "end") {
                handleTypeR(writeBackMemory)
            }
        }
        if (writeBackMemory.type == Type.I) {
            if (writeBackMemory.op == "lw") {
                lw(writeBackMemory)
            }
        }

        writeBack = writeBackMemory
        writeBackMemory = Word("noop")
    }

    private fun handleTypeR(word: Word) {
        val valor: Int
        when (word.op) {
            "add" -> {
                valor = (registers.get(word.r2).value + registers.get(word.r3).value)
                registers.get(word.r1).value = (valor)
            }

            "sub" -> {
                valor = (registers.get(word.r2).value - registers.get(word.r3).value)
                registers.get(word.r1).value = (valor)
            }

            "mult" -> {
                valor = (registers.get(word.r2).value * registers.get(word.r3).value)
                registers.get(word.r1).value = (valor)
            }

            "div" -> {
                valor = (registers.get(word.r2).value / registers.get(word.r3).value)
                registers.get(word.r1).value = (valor)
            }

            "noop" -> println("Noop Tipo R")
        }
    }

    private fun handleTypeI(word: Word) {
        when (word.op) {
            "lw" -> word.temp = (memory[word.offset + registers.get(word.r2).value]!!.toInt())
            "sw" -> word.temp = (registers.get(word.r2).value)
            "bne" -> {
                unlockRegisters(word)
                if (registers.get(word.r1).value != registers.get(word.r2).value) {
                    pc = word.offset
                    decodSearch = Word("noop")
                    searchingWord = Word("noop")
                    jump = true
                }
            }

            "beq" -> {
                unlockRegisters(word)
                if (registers.get(word.r1).value == registers.get(word.r2).value) {
                    pc = word.offset
                    decodSearch = Word("noop")
                    searchingWord = Word("noop")
                    jump = true
                }
            }

            "bltz" -> {
                unlockRegisters(word)
                if (registers.get(word.r1).value < 0) {
                    pc = word.offset
                    decodSearch = Word("noop")
                    searchingWord = Word("noop")
                    jump = true
                }
            }

            "bgtz" -> {
                unlockRegisters(word)
                if (registers.get(word.r1).value > 0) {
                    pc = word.offset
                    decodSearch = Word("noop")
                    searchingWord = Word("noop")
                    jump = true
                }
            }
        }
    }

    private fun handleTypeJ(word: Word) {
        jump = true
        when (word.op) {
            "j" -> {
                pc = word.offset
                decodSearch = Word("noop")
                searchingWord = Word("noop")
                jump = true
            }

            "jr" -> {
                pc = registers.get(word.r1).value
                decodSearch = Word("noop")
                searchingWord = Word("noop")
                jump = true
            }
        }
    }

    private fun lw(word: Word) {
        registers.get(word.r1).value = (word.temp)
    }

    private fun sw(word: Word) {
        memory[registers.get(word.r1).value + word.offset] = java.lang.String.valueOf(word.temp)
    }

    private fun handleTypeRBinary(aux: Array<String>) {
        val op = "000000"

        val rt = StringBuilder(Integer.toBinaryString(aux[3].substring(1).toInt()))
        val rd = StringBuilder(Integer.toBinaryString(aux[1].substring(1).toInt()))
        val rs = StringBuilder(Integer.toBinaryString(aux[2].substring(1).toInt()))

        while (rt.length < 5) {
            rt.insert(0, "0")
        }
        while (rd.length < 5) {
            rd.insert(0, "0")
        }
        while (rs.length < 5) {
            rs.insert(0, "0")
        }
        var funct: String? = null

        when (aux[0]) {
            "add" -> funct = "100000"
            "sub" -> funct = "100010"
            "mult" -> {}
            "div" -> {}
        }
        val shamt = "00000"

        val word = StringBuilder(op)
        word.append(rs)
        word.append(rt)
        word.append(rd)
        word.append(shamt)
        word.append(funct)

        binaryInstructions.add(word)
    }

    private fun handleTypeJ(aux: Array<String>) {
        val word = StringBuilder()
        val op: String?
        val immediate = StringBuilder()
        when (aux[0]) {
            "j" -> {
                op = "000010"
                immediate.append(Integer.toBinaryString(aux[1].toInt()))
                while (immediate.length < 26) {
                    immediate.insert(0, "0")
                }
                word.append(op)
                word.append(immediate)
            }

            "jr" -> {
                word.append("00000")
                immediate.append(Integer.toBinaryString(aux[1].toInt()))
                while (immediate.length < 26) {
                    immediate.insert(0, "0")
                }
                word.append(immediate)
                word.append("000000000000000001000")
            }
        }
        binaryInstructions.add(word)
    }

    private fun handleTypeIBinary(aux: Array<String>) {
        val word = StringBuilder()
        val div = aux[2].split("\\(".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val r1 = StringBuilder()
        val r2 = StringBuilder()

        val offset = StringBuilder()
        var op: String? = null

        r1.append(Integer.toBinaryString(aux[1].substring(1).toInt()))

        when (aux[0]) {
            "sw" -> {
                op = "101011"
                offset.append(Integer.toBinaryString(div[0].toInt()))
                r2.append(Integer.toBinaryString(div[1].substring(1, div[1].length - 1).toInt()))
            }

            "lw" -> {
                op = "100011"
                offset.append(Integer.toBinaryString(div[0].toInt()))
                r2.append(Integer.toBinaryString(div[1].substring(1, div[1].length - 1).toInt()))
            }

            "beq" -> {
                op = "000100"
                r2.append(Integer.toBinaryString(aux[2].substring(1).toInt()))
                offset.append(Integer.toBinaryString(aux[3].toInt()))
            }

            "bne" -> {
                op = "000101"
                r2.append(Integer.toBinaryString(aux[2].substring(1).toInt()))
                offset.append(Integer.toBinaryString(aux[3].toInt()))
            }

            "bltz" -> {
                op = "000001"
                r2.append(Integer.toBinaryString(aux[2].substring(1).toInt()))
                offset.append(Integer.toBinaryString(aux[3].toInt()))
            }

            "bgtz" -> {
                r2.append(Integer.toBinaryString(aux[2].substring(1).toInt()))
                offset.append(Integer.toBinaryString(aux[3].toInt()))
                op = "000111"
            }
        }

        while (r1.length < 5) {
            r1.insert(0, "0")
        }

        while (offset.length < 16) {
            offset.insert(0, "0")
        }

        while (r2.length < 5) {
            r2.insert(0, "0")
        }

        word.append(op)

        if (aux[0] == "sw" || aux[0] == "lw") {
            word.append(r2)
            word.append(r1)
        } else {
            word.append(r1)
            word.append(r2)
        }

        word.append(offset)
        binaryInstructions.add(word)
    }

    private fun handleTypeDBinary(aux: Array<String>) {
        val word = StringBuilder()
        when (aux[0]) {
            "noop" -> word.append("00000000000000000000000000000000")
            "get_tc" -> {
                word.append("111111")
                val memory = StringBuilder(Integer.toBinaryString(aux[1].toInt()))
                while (memory.length < 10) {
                    memory.insert(0, "0")
                }
                word.append(memory)
                word.append("0000000000000000")
            }
        }
        binaryInstructions.add(word)
    }
}
