package com.example.sudoku.model

import androidx.room.Entity

@Entity
data class Cell(
    var value: Int,
    val row: Int,
    val col: Int,
    val sol: Int,
    var click: Boolean = false,
    val note: Set<Int> = emptySet()
)
