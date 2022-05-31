package com.example.sudoku.model

import androidx.room.Entity

@Entity
data class Game(
    val difficult: String,
    val sudoku: Array<IntArray>,
    val solution: Array<IntArray>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (difficult != other.difficult) return false
        if (!sudoku.contentDeepEquals(other.sudoku)) return false
        if (!solution.contentDeepEquals(other.solution)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = difficult.hashCode()
        result = 31 * result + sudoku.contentDeepHashCode()
        result = 31 * result + solution.contentDeepHashCode()
        return result
    }
}
