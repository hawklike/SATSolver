package solver

data class SimulatedAnnealingConfig(
    val initialTemp: Double,
    val minTemp: Double,
    val coolingCoefficient: Double,
    val innerCycle: Int
)