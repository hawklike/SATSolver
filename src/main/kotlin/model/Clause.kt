package model

data class Clause(val literals: List<Literal>) {
    fun isFeasible() = literals.any { it.value }
}
