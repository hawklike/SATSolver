import solver.SimulatedAnnealing
import solver.SimulatedAnnealingConfig
import validation.Validator

fun main() {
    val formula = Validator.loadFormula("../wuf-M1/wuf50-201-M1", "wuf50-012.mwcnf")

    repeat(50) {
        val config = SimulatedAnnealingConfig(1000.0, 1.0, 0.995, 30)
        val solver = SimulatedAnnealing(config, formula)
        println(solver.solve())
    }
}