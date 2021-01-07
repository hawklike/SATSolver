package model

import util.Randomizer

class Formula(val filename: String, val optimum: Int?) : Comparable<Formula> {
    val variables: MutableList<Variable> = mutableListOf()
    val clauses: MutableList<Clause> = mutableListOf()

    private var hitThePeak = false

    val totalWeight: Int
        get() = variables.fold(0) { total, variable ->
            if(variable.present) total + variable.weight
            else total
        }

    val isSatisfiable: Boolean
        get() = if(clauses.isEmpty()) throw UnsupportedOperationException("Clauses are empty")
        else clauses.none { !it.isSatisfiable(variables) }

    val satisfiableClauses: Int
        get() = clauses.fold(0) { count, clause ->
            if(clause.isSatisfiable(variables)) count + 1
            else count
        }

    constructor(src: Formula) : this(src.filename, src.optimum) {
        src.variables.forEach { this.variables.add(it.copy()) }
        this.clauses.addAll(src.clauses)
    }

    fun toggleVariableSmart(position: Int) {
        if(hitThePeak) {
            toggleVariable(position)
            return
        }
        val clause = clauses.firstOrNull { !it.isSatisfiable(variables) }
        if(clause != null) {
            val literal = clause.literals.random()
            variables[literal.variableIdx].present = !variables[literal.variableIdx].present
        } else {
            hitThePeak = true
            variables[position].present = !variables[position].present
        }
    }

    fun toggleVariable(position: Int) {
        variables[position].present = !variables[position].present
    }

    fun toggleAllVariables(probability: Int) {
        for(i in variables.indices) {
            if(Randomizer.oneTo(probability)) toggleVariable(i)
        }
    }

    fun toggleSomeVariables(count: Int) {
        repeat(count) {
            val position = Randomizer.nextInt(variables.size)
            toggleVariable(position)
        }
    }

    override fun compareTo(other: Formula): Int {
        return if(this.satisfiableClauses == other.satisfiableClauses) this.totalWeight - other.totalWeight
        else this.satisfiableClauses - other.satisfiableClauses
    }
}
