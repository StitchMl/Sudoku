package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sudoku.model.Score
import kotlinx.coroutines.*

class GameRepository(private val gameDao: GameDao) {

    val allScore: LiveData<List<Score>> = gameDao.getAllScore()
    val searchScoreResults = MutableLiveData<List<Score>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertScore(newScore: Score) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.insertScore(newScore)
        }
    }

    fun deleteScoreDiff(diff: String) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.deleteDiffScore(diff)
        }
    }

    fun findScore(diff: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchScoreResults.value = asyncFind(diff).await()
        }
    }

    private fun asyncFind(diff: String): Deferred<List<Score>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async gameDao.findDiffScore(diff)
        }

    /*fun insertGame(newGame: Game) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.insertGame(newGame)
        }
    }*/

    /*fun deleteGame(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.deleteGame(id)
        }
    }

    fun findGame(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(id).await()
        }
    }

    private fun asyncFind(id: Int): Deferred<List<Game>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async gameDao.findGame(id)
        }*/
}