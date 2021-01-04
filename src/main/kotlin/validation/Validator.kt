package validation

import model.Formula
import parser.Parser
import solver.SimulatedAnnealing
import solver.SimulatedAnnealingConfig
import solver.SimulatedAnnealingConfig.GeneratingNewState.TOGGLE_ONE
import util.StopwatchCPU

class Validator {

    fun testGeneratingNewState(formula: Formula) {
        val config = SimulatedAnnealingConfig(1000.0, 1.0, 0.995, 30, TOGGLE_ONE)
        val solver = SimulatedAnnealing(config, formula)

        repeat(50) {
            val timer = StopwatchCPU(StopwatchCPU.IN_MILLISECONDS)
        }
    }

    companion object {
        fun loadFormula(base: String, filename: String) =
            Parser(base).parse(filename)
    }
}