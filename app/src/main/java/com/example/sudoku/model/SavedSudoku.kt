package com.example.sudoku.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sudoku")
data class SavedSudoku(
    var diff: String,
    var mistakes: Int,
    var time: Long
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "gameId")
    var id: Int = 0
}
