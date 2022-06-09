package com.example.sudoku.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.model.Score

class ScoreViewModel(application: Application): ViewModel() {
    val allScore: LiveData<List<Score>>
    val allSudoku: LiveData<List<SavedSudoku>>
    private val repository: GameRepository
    val searchScoreResults: MutableLiveData<List<Score>>
    val searchSudokuResults: MutableLiveData<List<SavedSudoku>>

    init {
        val gameDb = GameDatabase.getInstance(application)
        val gameDao = gameDb.gameDao()
        repository = GameRepository(gameDao)

        allScore = repository.allScore
        allSudoku = repository.allSudoku
        searchScoreResults = repository.searchScoreResults
        searchSudokuResults = repository.searchSudokuResults
    }

    fun insertScore(score: Score) {
        repository.insertScore(score)
    }

    fun findScore(diff: String) {
        repository.findScore(diff)
    }

    fun deleteScoreDiff(diff: String) {
        repository.deleteScoreDiff(diff)
    }

    fun insertSudoku(sudoku: SavedSudoku) {
        repository.insertSudoku(sudoku)
    }

    fun findSudoku(id: Int) {
        repository.findSudoku(id)
    }

    fun deleteSudoku(id: Int) {
        repository.deleteSudoku(id)
    }
}