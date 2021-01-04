import parser.Parser
import solver.SimulatedAnnealing
import solver.SimulatedAnnealingConfig

fun main(args: Array<String>) {
    val parser = Parser("../wuf-M1/wuf50-201-M1")
    val formula = parser.parse("wuf50-012.mwcnf")

    repeat(1000) {
        val config = SimulatedAnnealingConfig(1000.0, 10.0, 0.995, 30)
        val solver = SimulatedAnnealing(config, formula)
        println(solver.solve())
    }
}