package model

data class SATResult(
    val totalWeight: Int,
    val satisfiedClauses: Int,
    val allSatisfied: Boolean,
    val time: Double
) {
    override fun toString(): String {
        return """
            weight: $totalWeight
            satisfiedClauses: $satisfiedClauses
            allSatisfied: $allSatisfied
            time: $time
        """.trimIndent()
    }
}