package parser

import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import java.nio.file.Paths

class OutputWriter(private val base: String) {

    fun appendToEnd(filename: String, what: String) {
        Files.createDirectories(Paths.get(base))
        FileOutputStream(File("$base/$filename"), true).bufferedWriter().use { writer ->
            writer.appendLine(what)
        }
    }

    fun appendToEnd(filename: String, what: Int) {
        appendToEnd(filename, what.toString())
    }
}