
import validation.Validator

fun main() {
    val formulas = Validator.loadFormulas("../wuf-M1/wuf50-201-M1", 100)
    val validator = Validator()
    validator.testGeneratingNewState(formulas)
}