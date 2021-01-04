package solver

import Configuration
import model.Formula
import model.SATResult
import util.Randomizer
import util.StopwatchCPU
import kotlin.math.exp
import kotlin.random.Random

class SimulatedAnnealing(config: SimulatedAnnealingConfig, formula: Formula) {
    private val initialTemperature = config.initialTemp
    private val minTemperature = config.minTemp
    private val coolingCoefficient = config.coolingCoefficient
    private val equilibrium = config.innerCycle
    private val strategy = config.strategy

    private var state = formula
    private var bestState = Formula()

    private var temperature = initialTemperature

    fun solve(): SATResult {
        val timer = StopwatchCPU(StopwatchCPU.IN_MILLISECONDS)

        while(temperature > minTemperature) {
            var innerCycle = 0

            while(innerCycle++ < equilibrium) {
                state = createNewState()
                if(state > bestState) bestState = state
            }
            temperature *= coolingCoefficient
        }

        val time = timer.elapsedTime()
        with(bestState) {
            return SATResult(totalWeight, satisfiableClauses, isSatisfiable, time)
        }
    }

    private fun createNewState(): Formula {
        val newState = Formula(state)
        toggleVariables(newState)

        val newSatisfiableClauses = newState.satisfiableClauses
        val oldSatisfiableClauses = state.satisfiableClauses

        if(newSatisfiableClauses == oldSatisfiableClauses) {
            return if(isNewStateAccepted(newState.totalWeight, state.totalWeight)) newState
            else state
        }

        return if(isNewStateAccepted(newSatisfiableClauses, oldSatisfiableClauses)) newState
        else state
    }

    private fun toggleVariables(state: Formula) {
        val position = Randomizer.nextInt(state.variables.size)
        when(strategy) {
            SimulatedAnnealingConfig.GeneratingNewState.TOGGLE_ONE -> {
                state.toggleVariable(position)
            }
            SimulatedAnnealingConfig.GeneratingNewState.TOGGLE_N -> {
                state.toggleSomeVariables(state.variables.size / Configuration.toggleCount)
            }
            SimulatedAnnealingConfig.GeneratingNewState.TOGGLE_ALL -> {
                state.toggleAllVariables(Configuration.generatingProbability)
            }
        }
    }

    private fun isNewStateAccepted(new: Int, current: Int): Boolean {
        if(new > current) return true
        return Random.nextDouble() < exp((new - current) / temperature)
    }
}
