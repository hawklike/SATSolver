import model.Formula
import solver.SimulatedAnnealingConfig

class SimulatedAnnealing(config: SimulatedAnnealingConfig, formula: Formula) {
    private val initialTemperature = config.initialTemp
    private val minTemperature = config.minTemp
    private val coolingCoefficient = config.coolingCoefficient
    private val equilibrium = config.innerCycle

    fun solve() {

    }

    inner class State {

    }
}
