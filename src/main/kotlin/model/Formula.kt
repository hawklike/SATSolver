package model

class Formula {
    val variables: MutableList<Variable> = mutableListOf()
    val clauses: MutableList<Clause> = mutableListOf()

    fun isFeasible() =
        if(clauses.isEmpty()) throw UnsupportedOperationException("Clauses are empty")
        else clauses.none { !it.isFeasible() }
}
