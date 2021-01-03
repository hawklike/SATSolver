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

    fun solve(): Int {
        var temperature = initialTemperature

        while(temperature > minTemperature) {
            var innerCycle = 0

            while(innerCycle++ < equilibrium) {
                state = createNewState(temperature)
                if(state.isSatisfiable && state.totalWeight > bestState.totalWeight) {
                    bestState = state
                }
            }
            temperature *= coolingCoefficient
        }

        return bestState.totalWeight
    }

    private fun createNewState(temperature: Double): Formula {
        val position = ThreadLocalRandom.current().nextInt(0, state.variables.size)
        val newState = Formula(state)

        newState.toggleVariable(position)

        val delta = newState.totalWeight - state.totalWeight
        return if(Random.nextDouble() < exp(delta / temperature)) newState
        else state
    }
}
