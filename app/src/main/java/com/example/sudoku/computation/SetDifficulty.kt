package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlin.random.Random

class Setting(context: Context) {
    val difficulty: Array<String> = getDifficult(context)

    private fun getDifficult(context: Context) : Array<String>{
        val arr = Array(Difficulty.values().size){""}

        for (i in 0 until Difficulty.values().size){
            arr[i] = context.getString(Difficulty.values()[i].s)
        }

        return arr
    }

    fun setDifficult(d: String, diff: MutableState<Int>) {
        for(i in difficulty.indices) {
            when (d) {
                // Number of Empty Cell on all Sudoku
                difficulty[i] -> { diff.value = Random.nextInt((i * 10) + 20, (i * 10) + 30) }
            }
        }
    }
}
