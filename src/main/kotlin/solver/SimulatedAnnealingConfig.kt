package solver

data class SimulatedAnnealingConfig(
    val initialTemp: Double,
    val minTemp: Double,
    val coolingCoefficient: Double,
    val innerCycle: Int,
    val strategy: GeneratingNewState = GeneratingNewState.TOGGLE_ONE_SMART
) {
    enum class GeneratingNewState {
        TOGGLE_ONE,
        TOGGLE_ONE_SMART,
        TOGGLE_N,
        TOGGLE_ALL
    }

    override fun toString(): String {
        return "${initialTemp.toString().replace(".", ",")}-${
            minTemp.toString().replace(".", ",")
        }-${coolingCoefficient.toString().replace(".", ",")}-$innerCycle"
    }
}