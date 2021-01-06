import solver.SimulatedAnnealingConfig
import validation.Validator

fun main() {
//    val formulas = Validator.loadFormulas(Configuration.baseInput50, 100)
    val validator = Validator()
//    validator.testGeneratingNewState(formulas)

//    validator.showProgress(
//        SimulatedAnnealingConfig(250.0, 0.3, 0.995, 40),
//        Validator.loadFormula(Configuration.baseInput50, "wuf50-0123.mwcnf")
//    )
//    validator.showProgress(
//        SimulatedAnnealingConfig(250.0, 0.3, 0.995, 60),
//        Validator.loadFormula(Configuration.baseInput75, "wuf75-01.mwcnf")
//    )

//    repeat(1) {
//        validator.solve( SimulatedAnnealingConfig(250.0, 0.2, 0.995, 40),
//            Validator.loadFormula(Configuration.baseInput50, "wuf20-01.mwcnf"))
//        validator.solve( SimulatedAnnealingConfig(250.0, 0.2, 0.995, 60),
//            Validator.loadFormula(Configuration.baseInput75, "wuf75-01.mwcnf"))
//    }

    val formulas =
        Validator.loadFormulasWithSolution(Configuration.baseInput20, Configuration.baseInput, "wuf20-78-M-opt.dat")
    validator.testResults(SimulatedAnnealingConfig(250.0, 0.3, 0.995, 16), formulas)

//    val formulas =
//        Validator.loadFormulasWithSolution(Configuration.baseInput50, Configuration.baseInput, "wuf50-201-M-opt.dat")
//
//    val formulas75 = Validator.loadFormulas(Configuration.baseInput75, 100)
//
//    repeat(3) {
//        validator.testEquilibrium(formulas)
////        validator.testEquilibrium(formulas75)
//    }
}