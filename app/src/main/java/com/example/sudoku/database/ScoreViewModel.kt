package com.example.sudoku.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.model.Score

class ScoreViewModel(application: Application): ViewModel() {
    val allScore: LiveData<List<Score>>
    val allSudoku: LiveData<List<SavedSudoku>>
    private val repository: GameRepository

    init {
        val gameDb = GameDatabase.getInstance(application)
        val gameDao = gameDb.gameDao()
        repository = GameRepository(gameDao)

        allScore = repository.allScore
        allSudoku = repository.allSudoku
    }

    fun insertScore(score: Score) {
        repository.insertScore(score)
    }

    fun deleteScoreDiff(diff: String) {
        repository.deleteScoreDiff(diff)
    }

    fun insertSudoku(sudoku: SavedSudoku) {
        repository.insertSudoku(sudoku)
    }

    fun deleteSudoku(id: Int) {
        repository.deleteSudoku(id)
    }
}