package com.example.sudoku.database

import androidx.lifecycle.LiveData
import com.example.sudoku.model.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameRepository(private val gameDao: GameDao) {

    val allScore: LiveData<List<Score>> = gameDao.getAllScore()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val dis = Dispatchers.IO

    fun insertScore(newScore: Score) {
        coroutineScope.launch(dis) {
            gameDao.insertScore(newScore)
        }
    }

    fun deleteScoreById(id: Int) {
        coroutineScope.launch(dis) {
            gameDao.deleteScoreById(id)
        }
    }

    fun deleteScore(score: Score) {
        coroutineScope.launch(dis) {
            gameDao.deleteScore(score)
        }
    }
}