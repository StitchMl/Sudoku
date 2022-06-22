package com.example.sudoku.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sudoku.model.Score

class ScoreViewModel(application: Application): ViewModel() {
    val allScore: LiveData<List<Score>>
    val searchResults: MutableLiveData<List<Score>>
    private val repository: GameRepository

    init {
        val gameDb = GameDatabase.getInstance(application)
        val gameDao = gameDb.gameDao()
        repository = GameRepository(gameDao)

        allScore = repository.allScore
        searchResults = repository.searchResults
    }

    fun insertScore(score: Score) {
        repository.insertScore(score)
    }

    suspend fun findScoreById(id: Int) {
        repository.findScoreById(id)
    }

    fun deleteScoreById(id: Int) {
        repository.deleteScoreById(id)
    }

    fun deleteScore(score: Score) {
        repository.deleteScore(score)
    }
}