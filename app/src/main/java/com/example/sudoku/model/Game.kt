package com.example.sudoku.model

import androidx.annotation.NonNull
import androidx.compose.runtime.MutableState
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class Game(
    @ColumnInfo(name = "diff")
    var difficult: String = "",
    @ColumnInfo(name = "mistakes")
    var mistakes: Int = 0,
    @ColumnInfo(name = "time")
    var elapsedTime: Long = 0L,
) {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "gameId")
    var id: Int = 0

    @Ignore
    lateinit var sudoku: Array<Array<Cell>>
    @Ignore
    lateinit var bar: NumberBar
    @Ignore
    lateinit var counter: MutableState<Int>
    var oneSelect: Boolean = false
    @Ignore
    var iSelect: Int? = null
    @Ignore
    var jSelect: Int? = null

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