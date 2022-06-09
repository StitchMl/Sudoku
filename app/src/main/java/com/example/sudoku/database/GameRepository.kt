package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.model.Score
import kotlinx.coroutines.*

class GameRepository(private val gameDao: GameDao) {

    val allScore: LiveData<List<Score>> = gameDao.getAllScore()
    val searchScoreResults = MutableLiveData<List<Score>>()
    val allSudoku: LiveData<List<SavedSudoku>> = gameDao.getAllSudoku()
    val searchSudokuResults = MutableLiveData<List<SavedSudoku>>()
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

    fun insertSudoku(newSudoku: SavedSudoku) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.insertSudoku(newSudoku)
        }
    }

    fun deleteSudoku(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            gameDao.deleteSudoku(id)
        }
    }

    fun findSudoku(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchSudokuResults.value = asyncSudoku(id).await()
        }
    }

    private fun asyncSudoku(id: Int): Deferred<List<SavedSudoku>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async gameDao.findSudoku(id)
        }
}