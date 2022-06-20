package com.example.sudoku.model

import androidx.compose.runtime.MutableState

data class Game(
    var difficult: String = "",
    var mistakes: MutableState<Int>,
    var elapsedTime: Long = 0L,
    var sudoku: Array<Array<Cell>>,
    var bar: NumberBar,
    var counter: MutableState<Int>,
    var oneSelect: Boolean = false,
    var iSelect: Int? = null,
    var jSelect: Int? = null,
    var solution: Array<IntArray> = Array(9){IntArray(9)},
    var note : Action
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Game

        if (difficult != other.difficult) return false
        if (!sudoku.contentDeepEquals(other.sudoku)) return false
        if (bar != other.bar) return false
        if (oneSelect != other.oneSelect) return false
        if (iSelect != other.iSelect) return false
        if (jSelect != other.jSelect) return false

        return true
    }

    override fun hashCode(): Int {
        var result = difficult.hashCode()
        result = 31 * result + sudoku.contentDeepHashCode()
        result = 31 * result + bar.hashCode()
        result = 31 * result + oneSelect.hashCode()
        result = 31 * result + (iSelect ?: 0)
        result = 31 * result + (jSelect ?: 0)
        return result
    }

    override fun toString(): String {
        return super.toString()
    }
}