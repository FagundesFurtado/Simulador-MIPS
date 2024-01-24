package controller

import view.UiComponent
import java.io.*
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import javax.swing.JFileChooser
import javax.swing.JOptionPane

class MipsSimulator {
    private var instructions: ArrayList<String> = ArrayList()
    private var binaryInstruction: ArrayList<StringBuilder>? = null
    private lateinit var xeon: Processor
    private var uiComponent: UiComponent = UiComponent(this)

    @Throws(IOException::class)
    fun readFiles() {
        instructions.clear()

        val jfChooser = JFileChooser()
        jfChooser.currentDirectory = File(".")
        val output = jfChooser.showOpenDialog(null)

        if (output == JFileChooser.APPROVE_OPTION) {
            val path = jfChooser.selectedFile.absolutePath

            println(path)

            var fileInput: FileInputStream? = null
            try {
                fileInput = FileInputStream(path)
            } catch (ex: FileNotFoundException) {
                Logger.getLogger(MipsSimulator::class.java.name).log(Level.SEVERE, null, ex)
            }

            val input = InputStreamReader(fileInput!!)
            val reader = BufferedReader(input)

            while (true) {
                val line = reader.readLine()
                if (line != null) {
                    instructions.add(line)
                } else {
                    break
                }
            }

            xeon = Processor(instructions, this)
            uiComponent.start(xeon)
        } else {
            JOptionPane.showMessageDialog(null, "Sem entrada de fileInput válida.")
        }
    }

    fun storeInDisk() {
        binaryInstruction = xeon.binaryInstructions

        val c = Calendar.getInstance()
        val data =
            c[Calendar.HOUR].toString() + "." +
                c[Calendar.MINUTE].toString() + "." + c[Calendar.DATE].toString() + "." +
                (c[Calendar.MONTH] + 1).toString()

        try {
            val pathFolder = "./src/Output/"
            val pathFile = "$data.fera"

            val file = File(pathFolder + pathFile)
            file.parentFile.mkdir()
            file.createNewFile()

            BufferedWriter(FileWriter(file, false)).use { bw ->
                for (i in binaryInstruction!!) {
                    bw.write(i.toString())
                    bw.write("\n")
                    bw.flush()
                }
            }
        } catch (_: IOException) {
        }
    }
}
