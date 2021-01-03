package model

data class Literal(val negative: Boolean, private val variable: Variable) {
    val value = if (negative) !variable.value else variable.value
}
