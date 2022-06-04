package com.example.sudoku.model

import androidx.compose.runtime.MutableState
import androidx.room.Entity

@Entity
data class Game(
    var difficult: MutableState<String>,
    val sudoku: Array<Array<Cell>>,
    val bar: NumberBar,
    val mistakes: Int = 0,
    val elapsedTime: Long = 0,
    var oneSelect: Boolean = false,
    var i_Select: Int? = null,
    var j_Select: Int? = null,
    var counter: MutableState<Int>
) {
    val elapsedTimeString: String
        get() {
            val secs = elapsedTime / 1000 % 60
            val mins = elapsedTime / 1000 / 60

            return "${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}"
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (difficult != other.difficult) return false
        if (!sudoku.contentDeepEquals(other.sudoku)) return false
        if (bar != other.bar) return false
        if (oneSelect != other.oneSelect) return false
        if (i_Select != other.i_Select) return false
        if (j_Select != other.j_Select) return false

        return true
    }

    override fun hashCode(): Int {
        var result = difficult.hashCode()
        result = 31 * result + sudoku.contentDeepHashCode()
        result = 31 * result + bar.hashCode()
        result = 31 * result + oneSelect.hashCode()
        result = 31 * result + (i_Select ?: 0)
        result = 31 * result + (j_Select ?: 0)
        return result
    }
}