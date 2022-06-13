package com.example.sudoku.model

import androidx.compose.runtime.MutableState

data class Cell(
    var row: Int,
    var col: Int,
    var sol: Int,
    var value: MutableState<Int>?,
    var click: MutableState<Int>?,
    var note: MutableState<Int>? = null
    //val note: Set<Int> = emptySet()
    //var notes: MutableState<Int>
)
