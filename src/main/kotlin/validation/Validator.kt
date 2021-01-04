package validation

import model.Formula
import parser.OutputWriter
import parser.Parser
import solver.SimulatedAnnealing
import solver.SimulatedAnnealingConfig
import solver.SimulatedAnnealingConfig.GeneratingNewState
import solver.SimulatedAnnealingConfig.GeneratingNewState.*

class Validator {

    fun testGeneratingNewState(formulas: List<Formula>) {
        testGeneratingNewState(TOGGLE_ONE, formulas)
        testGeneratingNewState(TOGGLE_ALL, formulas)
        testGeneratingNewState(TOGGLE_N, formulas)
    }

    private fun testGeneratingNewState(strategy: GeneratingNewState, formulas: List<Formula>) {
        val config = SimulatedAnnealingConfig(1000.0, 1.0, 0.995, 30, strategy)

        val weight = mutableListOf<Int>()
        val satisfiedClauses = mutableListOf<Int>()
        val allSatisfied = mutableListOf<Boolean>()
        val time = mutableListOf<Double>()

        formulas.forEach { formula ->
            val res = SimulatedAnnealing(config, formula).solve()
            weight.add(res.totalWeight)
            satisfiedClauses.add(res.satisfiedClauses)
            allSatisfied.add(res.allSatisfied)
            time.add(res.time)

            println(res)
            println("-------------------------")
        }

        val stats = """
            avg time in ms: ${time.average()}
            avg satisfied clauses: ${satisfiedClauses.average()}
            avg max weight: ${weight.average()}
            satisfied probability: ${allSatisfied.sumBy { if(it) 1 else 0 } / formulas.size.toDouble()}
        """.trimIndent()

        OutputWriter("../output/new_state_generating/all", "${config.strategy.name}.txt").appendToEnd(stats)
    }

    companion object {
        fun loadFormula(base: String, filename: String) =
            Parser(base).parse(filename)

        fun loadFormulas(base: String, count: Int) =
            Parser(base).parseAll(count)

        fun loadAllFormulas(base: String) =
            Parser(base).parseAll()
    }
}