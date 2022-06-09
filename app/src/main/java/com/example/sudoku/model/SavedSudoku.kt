package com.example.sudoku.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sudoku")
data class SavedSudoku(
    @ColumnInfo(name = "difficulty")
    var diff: String,
    @ColumnInfo(name = "mistakes")
    var mistakes: Int,
    @ColumnInfo(name = "time")
    var time: Long,
    @ColumnInfo(name = "sudoku")
    var sudoku: Array<IntArray>,
    @ColumnInfo(name = "solution")
    var solution: Array<IntArray>
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gameId")
    var id: Int = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SavedSudoku

        if (diff != other.diff) return false
        if (mistakes != other.mistakes) return false
        if (time != other.time) return false
        if (!sudoku.contentDeepEquals(other.sudoku)) return false
        if (!solution.contentDeepEquals(other.solution)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = diff.hashCode()
        result = 31 * result + mistakes
        result = 31 * result + time.hashCode()
        result = 31 * result + sudoku.contentDeepHashCode()
        result = 31 * result + solution.contentDeepHashCode()
        result = 31 * result + id
        return result
    }
}
