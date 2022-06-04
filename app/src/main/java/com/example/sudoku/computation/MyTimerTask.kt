package com.example.sudoku.computation

import androidx.compose.runtime.MutableState
import java.util.*


internal class MyTimerTask(private val timer: MutableState<Long>, private val start: MutableState<Boolean>) : TimerTask() {
    override fun run() {
        if(start.value) {
            timer.value++
        }
    }
}