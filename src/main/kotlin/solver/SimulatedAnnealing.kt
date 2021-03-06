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
    private val strategy = config.strategy

    private var state = formula
    private var bestState = Formula(formula.filename, formula.optimum)

    private var temperature = initialTemperature
    private var equilibrium = config.innerCycle

    private var progress: ((clauses: Int, weight: Int) -> Unit)? = null

    fun withProgress(callback: (clauses: Int, weight: Int) -> Unit): SimulatedAnnealing {
        progress = callback
        return this
    }

    fun solve(): SATResult {
        val timer = StopwatchCPU(StopwatchCPU.IN_MILLISECONDS)
        var resets = 0

        fun solveInner() {
            while(temperature > minTemperature) {
                var innerCycle = 0

                while(innerCycle++ < equilibrium) {
                    state = createNewState()
                    if(state > bestState) bestState = state

                    progress?.invoke(bestState.satisfiableClauses, bestState.totalWeight)
                }
                temperature *= coolingCoefficient
            }

            if(!bestState.isSatisfiable && resets++ < Configuration.resets) {
                temperature = initialTemperature
                equilibrium = (equilibrium * 1.25).toInt()
                solveInner()
            }
        }

        solveInner()

        val time = timer.elapsedTime()
        with(bestState) {
            println(filename)
            println("time: $time ms, totalWeight: $totalWeight, optimum: $optimum, clauses: $satisfiableClauses")
            return SATResult(totalWeight, satisfiableClauses, isSatisfiable, time)
        }
    }

    private fun createNewState(): Formula {
        val newState = Formula(state)
        toggleVariables(newState)

        val newSatisfiableClauses = newState.satisfiableClauses
        val oldSatisfiableClauses = state.satisfiableClauses

        if(newSatisfiableClauses == oldSatisfiableClauses) {
            return if(isNewStateAccepted(newState.totalWeight, state.totalWeight, 100)) newState
            else state
        }

        return if(isNewStateAccepted(newSatisfiableClauses, oldSatisfiableClauses, 1)) newState
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
            SimulatedAnnealingConfig.GeneratingNewState.TOGGLE_ONE_SMART ->
                state.toggleVariableSmart(position)
        }
    }

    private fun isNewStateAccepted(new: Int, current: Int, coef: Int): Boolean {
        if(new > current) return true
        return Random.nextDouble() < exp((new - current) / coef * temperature)
    }
}
