package solver

data class SimulatedAnnealingConfig(
    val initialTemp: Double,
    val minTemp: Double,
    val coolingCoefficient: Double,
    val innerCycle: Int,
    val strategy: GeneratingNewState = GeneratingNewState.TOGGLE_ONE
) {
    enum class GeneratingNewState {
        TOGGLE_ONE,
        TOGGLE_N,
        TOGGLE_ALL
    }
}