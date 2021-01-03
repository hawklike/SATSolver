import parser.Parser
import solver.SimulatedAnnealing
import solver.SimulatedAnnealingConfig

fun main(args: Array<String>) {
    val parser = Parser("../wuf-M1/test")
    val formula = parser.parse("sample.txt")

    repeat(1000) {
        val config = SimulatedAnnealingConfig(1000.0, 10.0, 0.995, 10)
        val solver = SimulatedAnnealing(config, formula)
        println(solver.solve())
    }
}