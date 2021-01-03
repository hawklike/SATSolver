package model

data class Formula(val clauses: MutableList<Clause>, val variables: MutableList<Variable>) {
    fun isFeasible() = clauses.none { !it.isFeasible() }
}
