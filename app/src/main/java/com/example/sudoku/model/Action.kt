package com.example.sudoku.model

import androidx.compose.runtime.MutableState

data class Action (
    var note : MutableState<Boolean>,
    var r: Int,
    var c: Int
        )