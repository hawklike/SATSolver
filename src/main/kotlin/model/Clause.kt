package model

data class Clause(val literals: List<Literal>) {
    fun isSatisfiable(variables: List<Variable>) = literals.any { it.isTrue(variables) }
}
