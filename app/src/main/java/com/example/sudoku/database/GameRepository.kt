package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sudoku.model.Game
import com.example.sudoku.model.Score
import kotlinx.coroutines.*

class GameRepository(private val gameDao: GameDao) {

    val allGames: LiveData<List<Game>> = gameDao.getAllGames()
    val allScore: LiveData<List<Score>> = gameDao.getAllScore()
    val searchResults = MutableLiveData<List<Game>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertGame(newGame: Game) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.insertGame(newGame)
        }
    }

    fun deleteGame(id: Int) {
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
        }
}