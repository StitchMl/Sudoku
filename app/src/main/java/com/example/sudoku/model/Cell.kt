package com.example.sudoku.model

import androidx.compose.runtime.MutableState
import androidx.room.Entity

@Entity
data class Cell(
    var row: Int,
    var col: Int,
    var sol: Int,
    var value: MutableState<Int>?,
    var click: MutableState<Int>?,
    val note: Set<Int> = emptySet()
)
