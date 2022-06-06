package com.example.sudoku.model

import androidx.annotation.NonNull
import androidx.compose.runtime.MutableState
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cell")
data class Cell(
    var row: Int,
    var col: Int,
    var sol: Int,
    var value: MutableState<Int>?,
    var click: MutableState<Int>?,
    val note: Set<Int> = emptySet()
){
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "cellId")
    var id: Int = 0
}
