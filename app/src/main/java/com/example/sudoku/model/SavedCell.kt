package com.example.sudoku.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cell", primaryKeys = ["row", "col"])
data class SavedCell(
    @ColumnInfo(name = "row")
    var row: Int,
    @ColumnInfo(name = "col")
    var col: Int,
    var sol: Int,
    var value: Int
)
