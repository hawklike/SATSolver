import solver.SimulatedAnnealingConfig
import validation.Validator

fun main() {
    val validator = Validator()
    validator.testGeneratingNewState(Validator.loadFormulas(Configuration.baseInputM50, 100))

    val config = SimulatedAnnealingConfig(250.0, 0.3, 0.995, 16)

    validator.showProgress(
        config,
        Validator.loadFormula(Configuration.baseInputM50, "wuf50-0123.mwcnf")
    )

    repeat(10) {
        validator.solve(
            config,
            Validator.loadFormula(Configuration.baseInputM75, "wuf75-01.mwcnf")
        )
    }

    validator.solve(
        SimulatedAnnealingConfig(
            250.0,
            0.3,
            0.995,
            60,
            SimulatedAnnealingConfig.GeneratingNewState.TOGGLE_ONE_SMART
        ), Validator.loadFormulas(Configuration.baseInputA100, 100)
    )

    validator.testAdaptiveMechanism(Validator.loadFormulas(Configuration.baseInputA100, 100))


    validator.compareWithResults(
        config,
        Validator.loadFormulasWithSolution(Configuration.baseInputM20, Configuration.baseInputM, "wuf20-78-M-opt.dat")
    )

    repeat(3) {
        validator.getAverageEpsilon(
            SimulatedAnnealingConfig(250.0, 0.3, 0.995, 40),
            Validator.loadFormulasWithSolution(
                Configuration.baseInputM50,
                Configuration.baseInputM,
                "wuf50-201-M-opt.dat"
            )
        )
    }

    repeat(1) {
        validator.solve(
            SimulatedAnnealingConfig(250.0, 0.2, 0.995, 40),
            Validator.loadFormula(Configuration.baseInputM50, "wuf20-01.mwcnf")
        )
        validator.solve(
            SimulatedAnnealingConfig(250.0, 0.2, 0.995, 60),
            Validator.loadFormula(Configuration.baseInputM75, "wuf75-01.mwcnf")
        )
    }

    validator.compareWithResults(
        SimulatedAnnealingConfig(250.0, 0.3, 0.995, 16),
        Validator.loadFormulasWithSolution(Configuration.baseInputM20, Configuration.baseInputM, "wuf20-78-M-opt.dat")
    )

    repeat(3) {
        validator.testEquilibrium(
            Validator.loadFormulasWithSolution(
                Configuration.baseInputM50,
                Configuration.baseInputM,
                "wuf50-201-M-opt.dat"
            )
        )
        validator.testEquilibrium(Validator.loadFormulas(Configuration.baseInputM75, 100))
    }
}