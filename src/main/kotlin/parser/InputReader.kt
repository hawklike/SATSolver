package parser

import java.io.File

class InputReader(private val base: String) {

    fun getFiles(pattern: Regex) = File(base).listFiles { file -> file.name.matches(pattern) }

    fun getFiles(name: String) = File(base).listFiles { file -> file.name == name }

    fun getFile(pattern: Regex) = File(base).listFiles()?.find { it.name.matches(pattern) }

    fun getFile(name: String) = File(base).listFiles()?.find { it.name == name }
}