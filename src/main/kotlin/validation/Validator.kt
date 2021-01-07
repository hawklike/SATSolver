package validation

import Configuration
import model.Formula
import model.SATResult
import parser.OutputWriter
import parser.Parser
import parser.SolutionReader
import solver.SimulatedAnnealing
import solver.SimulatedAnnealingConfig
import solver.SimulatedAnnealingConfig.GeneratingNewState
import solver.SimulatedAnnealingConfig.GeneratingNewState.*
import kotlin.math.abs
import kotlin.math.max

class Validator {

    fun testAdaptiveMechanism(formulas: List<Formula>) {
        testGeneratingNewState(TOGGLE_ONE, formulas, SimulatedAnnealingConfig(250.0, 0.3, 0.995, 60, TOGGLE_ONE))
        testGeneratingNewState(
            TOGGLE_ONE_SMART,
            formulas,
            SimulatedAnnealingConfig(250.0, 0.3, 0.995, 60, TOGGLE_ONE_SMART)
        )
    }

    fun testGeneratingNewState(formulas: List<Formula>) {
        testGeneratingNewState(TOGGLE_ONE, formulas)
        testGeneratingNewState(TOGGLE_ALL, formulas)
        testGeneratingNewState(TOGGLE_N, formulas)
    }

    private fun testGeneratingNewState(
        strategy: GeneratingNewState,
        formulas: List<Formula>,
        _config: SimulatedAnnealingConfig? = null
    ) {
        val config = _config ?: SimulatedAnnealingConfig(1000.0, 1.0, 0.995, 30, strategy)

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

        OutputWriter("${Configuration.baseOutput}/adaptive_mechanism").appendToEnd(
            "${config.strategy.name}_100.txt",
            stats
        )
    }

    fun showProgress(config: SimulatedAnnealingConfig, formula: Formula) {
        SimulatedAnnealing(config, formula).withProgress { clauses, weight ->
            OutputWriter("${Configuration.baseOutput}/progress/smarter/toggleVariable").apply {
                appendToEnd("${config}_clauses_${formula.filename}_smarter.txt", clauses)
                appendToEnd("${config}_weight_${formula.filename}_smarter.txt", weight)
            }
        }.solve()
    }

    fun testResults(config: SimulatedAnnealingConfig, formulasWithSolutions: List<Formula>) {
        formulasWithSolutions.forEach { formula ->
            val res = SimulatedAnnealing(config, formula).solve()
            val ok = if(res.totalWeight == formula.optimum) "OK" else "FAIL"
            println("${formula.optimum} vs ${res.totalWeight} $ok")
        }
    }

    fun solve(config: SimulatedAnnealingConfig, formula: Formula): SATResult {
        return SimulatedAnnealing(config, formula).solve()
    }

    fun testEquilibrium(formulas: List<Formula>) {
        testEquilibrium(formulas[1].variables.size * 4 / 5, formulas)
        testEquilibrium(10, formulas)
        testEquilibrium(25, formulas)
        testEquilibrium(30, formulas)
        testEquilibrium(50, formulas)
        testEquilibrium(75, formulas)
        testEquilibrium(100, formulas)
        testEquilibrium(200, formulas)
    }

    private fun testEquilibrium(innerCycle: Int, formulas: List<Formula>) {
        val config = SimulatedAnnealingConfig(250.0, 0.2, 0.995, innerCycle)

        val epsilons = mutableListOf<Double>()
        val time = mutableListOf<Double>()
        val allSatisfied = mutableListOf<Boolean>()

        formulas.forEach { formula ->
            val res = solve(config, formula)
            formula.optimum?.let { epsilons.add(calculateEpsilon(it, res.totalWeight)) }
            time.add(res.time)
            allSatisfied.add(res.allSatisfied)

            println(res)
            println("-------------------------")
        }

        val stats = """
            avg time in ms: ${time.average()}
            avg epsilon: ${epsilons.average()}
            satisfied probability: ${allSatisfied.sumBy { if(it) 1 else 0 } / formulas.size.toDouble()}
        """.trimIndent()

        OutputWriter("${Configuration.baseOutput}/equilibrium").appendToEnd("$innerCycle.txt", stats)
    }

    private fun calculateEpsilon(reference: Int, computed: Int): Double {
        return (abs(reference - computed) / max(reference, computed).toDouble()).let {
            if(it.isNaN()) 0.0
            else it
        }
    }

    companion object {
        fun loadFormula(base: String, filename: String) =
            Parser(base).parse(filename)

        fun loadFormulas(base: String, count: Int) =
            Parser(base).parseAll(count)

        fun loadFormulas(base: String) =
            Parser(base).parseAll()

        fun loadFormulasWithSolution(base: String, solutionBase: String, solutionsFilename: String) =
            Parser(base).parseWithSolutions(SolutionReader(solutionBase).readSolutions(solutionsFilename))
    }
}