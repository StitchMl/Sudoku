package com.example.sudoku.model

import android.content.Context
import androidx.compose.runtime.MutableState
import com.example.sudoku.R
import kotlin.random.Random

class Setting(context: Context) {
    val DIFFICULTY: Array<String> =
        context.resources.getStringArray(R.array.difficulty)

    fun setDifficult(d: String, diff: MutableState<Int>) {
        for(i in DIFFICULTY.indices) {
            when (d) {
                // Number of Empty Cell on all Sudoku
                DIFFICULTY[i] -> { diff.value = Random.nextInt((i * 10) + 20, (i * 10) + 30) }
            }
        }
    }
}
