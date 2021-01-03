package model

data class Clause(val literals: List<Literal>) {
    fun isFeasible(variables: List<Variable>) = literals.any { it.isTrue(variables) }
}
