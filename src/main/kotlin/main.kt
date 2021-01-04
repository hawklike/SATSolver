import validation.Validator

fun main() {
//    val formulas = Validator.loadFormulas(Configuration.baseInput50, 100)
    val validator = Validator()
//    validator.testGeneratingNewState(formulas)

//    validator.showProgress(
//        SimulatedAnnealingConfig(250.0, 0.2, 0.995, 30),
//        Validator.loadFormula(Configuration.baseInput50, "wuf50-0123.mwcnf")
//    )

//    repeat(10) {
//        validator.solve( SimulatedAnnealingConfig(250.0, 0.2, 0.995, 30),
//            Validator.loadFormula(Configuration.baseInput50, "wuf50-0123.mwcnf"))
//    }

    val formulas =
        Validator.loadFormulasWithSolution(Configuration.baseInput50, Configuration.baseInput, "wuf50-201-M-opt.dat")

    println(formulas)
}