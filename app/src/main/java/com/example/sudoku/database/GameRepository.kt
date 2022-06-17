package com.example.sudoku.database

import androidx.lifecycle.LiveData
import com.example.sudoku.model.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameRepository(private val gameDao: GameDao) {

    val allScore: LiveData<List<Score>> = gameDao.getAllScore()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertScore(newScore: Score) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.insertScore(newScore)
        }
    }

    fun deleteScoreById(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.deleteScoreById(id)
        }
    }

    fun deleteScore(score: Score) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.deleteScore(score)
        }
    }
}