package com.example.sudoku.database

import androidx.lifecycle.LiveData
import com.example.sudoku.model.SavedCell
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.model.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameRepository(private val gameDao: GameDao) {

    val allScore: LiveData<List<Score>> = gameDao.getAllScore()
    val allSudoku: LiveData<List<SavedSudoku>> = gameDao.getAllSudoku()
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
}