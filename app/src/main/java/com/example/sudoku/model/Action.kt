package com.example.sudoku.model

import androidx.compose.runtime.MutableState

data class Action (
    var r: Int,
    var c: Int,
    var note : MutableState<Boolean>)