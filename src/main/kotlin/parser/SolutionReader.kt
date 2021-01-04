package parser

import java.io.File
import java.io.FileNotFoundException

class SolutionReader(private val base: String) {

    fun readSolutions(filename: String): Map<String, Int> {
        return InputReader(base).getFile(filename)?.let { parseSolutions(it) }
            ?: throw FileNotFoundException("$base/$filename not found")
    }

    private fun parseSolutions(file: File): Map<String, Int> {
        val solutions = mutableMapOf<String, Int>()
        file.forEachLine { line ->
            val (id, optimum) = with(line.trim().split(" ")) {
                get(0).split("-")[1] to get(1).toInt()
            }
            solutions[id] = optimum
        }
        return solutions
    }
}