package com.example.sudoku.computation

import com.example.sudoku.R

enum class Difficulty(val s: Int) {
    EASY(R.string.easy),
    NORMAL(R.string.normal),
    HARD(R.string.hard),
    EXPERT(R.string.expert)
}