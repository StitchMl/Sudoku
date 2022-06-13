package com.example.sudoku.computation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Score

@Composable
fun getScore(score: ScoreViewModel): List<Score>{
    val allScore by score.allScore.observeAsState(listOf())
    return allScore
}