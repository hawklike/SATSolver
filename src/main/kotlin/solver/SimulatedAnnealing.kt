package solver

import model.Formula
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.exp
import kotlin.random.Random

class SimulatedAnnealing(config: SimulatedAnnealingConfig, formula: Formula) {
    private val initialTemperature = config.initialTemp
    private val minTemperature = config.minTemp
    private val coolingCoefficient = config.coolingCoefficient
    private val equilibrium = config.innerCycle

    private var state = formula
    private var bestState = Formula()

    private var temperature = initialTemperature

    fun solve(): Int {
        while(temperature > minTemperature) {
            var innerCycle = 0

            while(innerCycle++ < equilibrium) {
                state = createNewState()
                if(state > bestState) bestState = state
            }
            temperature *= coolingCoefficient
        }

        return bestState.totalWeight
    }

    private fun createNewState(): Formula {
        val position = ThreadLocalRandom.current().nextInt(0, state.variables.size)
        val newState = Formula(state)
        newState.toggleVariable(position)

        val newSatisfiableClauses = newState.satisfiableClauses
        val oldSatisfiableClauses = state.satisfiableClauses

        if(newSatisfiableClauses == oldSatisfiableClauses) {
            return if(isNewStateAccepted(newState.totalWeight, state.totalWeight)) newState
            else state
        }

        return if(isNewStateAccepted(newSatisfiableClauses, oldSatisfiableClauses)) newState
        else state
    }

    private fun isNewStateAccepted(new: Int, current: Int): Boolean {
        if(new > current) return true
        return Random.nextDouble() < exp((new - current) / temperature)
    }
}
