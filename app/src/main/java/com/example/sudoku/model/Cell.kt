package com.example.sudoku.model

import androidx.annotation.NonNull
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Cell(
    var row: Int,
    var col: Int,
    var sol: Int,
    var value: MutableState<Int>?,
    var click: MutableState<Int>?,
    var note: MutableState<Int>?
    //val note: Set<Int> = emptySet()
    //var notes: MutableState<Int>
)
