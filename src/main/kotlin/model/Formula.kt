package model

class Formula() {
    val variables: MutableList<Variable> = mutableListOf()
    val clauses: MutableList<Clause> = mutableListOf()

    val totalWeight: Int
        get() = variables.fold(0) { total, variable ->
            if(variable.present) total + variable.weight
            else total
        }

    val isSatisfiable: Boolean
        get() = if(clauses.isEmpty()) throw UnsupportedOperationException("Clauses are empty")
        else clauses.none { !it.isFeasible(variables) }

    val feasibleClauses: Int
        get() = clauses.fold(0) { count, clause ->
            if(clause.isFeasible(variables)) count + 1
            else count
        }

    constructor(src: Formula) : this() {
        src.variables.forEach { this.variables.add(it.copy()) }
        this.clauses.addAll(src.clauses)
    }

    fun toggleVariable(position: Int) {
        variables[position].present = !variables[position].present
    }
}
