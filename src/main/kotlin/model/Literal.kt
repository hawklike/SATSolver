package model

data class Literal(val negative: Boolean, val variableIdx: Int) {
    fun isTrue(variables: List<Variable>) =
        if(negative) !variables[variableIdx].present
        else variables[variableIdx].present
}
