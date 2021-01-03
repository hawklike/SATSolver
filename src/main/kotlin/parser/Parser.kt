package parser

import model.Clause
import model.Formula
import model.Literal
import model.Variable
import java.io.File
import java.io.FileNotFoundException
import kotlin.math.abs

class Parser(private val base: String) {

    private val formula = Formula()

    fun parse(filename: String): Formula {
        return InputReader(base).getFile(filename)?.let { parseFormula(it) }
            ?: throw FileNotFoundException("$base/$filename not found")
    }

    private fun parseFormula(file: File): Formula {
        file.forEachLine { line ->
            when(line.trim().first()) {
                WEIGHT -> formula.variables.addAll(parseWeights(line.drop(WEIGHT.toString().length)))
                !in listOf(COMMENT, PRINT) -> formula.clauses.add(parseClause(line))
            }
        }
        return this.formula
    }

    private fun parseWeights(line: String): List<Variable> {
        return line.split(DELIMITER).fold(mutableListOf()) { acc, weight ->
            if(weight.isNotBlank() && weight != END_OF_LINE.toString()) {
                acc.add(Variable(weight.toInt()))
            }
            acc
        }
    }

    private fun parseClause(line: String): Clause {
        val literals = line.split(DELIMITER).fold(mutableListOf<Literal>()) { acc, literal ->
            if(literal.isNotBlank()) {
                literal.toInt().let { value ->
                    if(value != Character.getNumericValue(END_OF_LINE)) {
                        acc.add(Literal(value < 0, formula.variables[abs(value) - 1]))
                    }
                }
            }
            acc
        }
        return Clause(literals)
    }

    companion object Marker {
        private const val COMMENT = 'c'
        private const val PRINT = 'p'
        private const val WEIGHT = 'w'
        private const val DELIMITER = ' '
        private const val END_OF_LINE = '0'
    }

}