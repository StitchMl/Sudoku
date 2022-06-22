package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sudoku.model.Score
import kotlinx.coroutines.*

class GameRepository(private val gameDao: GameDao, private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    val allScore: LiveData<List<Score>> = gameDao.getAllScore()
    val searchResults = MutableLiveData<List<Score>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertScore(newScore: Score) {
        coroutineScope.launch(dispatcher) {
            gameDao.insertScore(newScore)
        }
    }

    suspend fun findScoreById(id: Int) {
        searchResults.value = findAsync(id).await()
    }

    fun deleteScoreById(id: Int) {
        coroutineScope.launch(dispatcher) {
            gameDao.deleteScoreById(id)
        }
    }

    fun deleteScore(score: Score) {
        coroutineScope.launch(dispatcher) {
            gameDao.deleteScore(score)
        }
    }

    private fun findAsync(id: Int): Deferred<List<Score>?> =
        coroutineScope.async(dispatcher) {
            return@async gameDao.findScoreById(id)
        }
}