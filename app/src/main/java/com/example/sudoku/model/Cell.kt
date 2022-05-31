package com.example.sudoku.model

import androidx.compose.runtime.MutableState
import androidx.room.Entity

@Entity
data class Cell(
    var value: Int,
    val row: Int,
    val col: Int,
    val sol: Int,
    var mutableValue: MutableState<Int>? = null,
    var click: MutableState<Boolean>? = null,
    val note: Set<Int> = emptySet()
)
