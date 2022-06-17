package com.example.sudoku.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sudoku.model.Score

class ScoreViewModel(application: Application): ViewModel() {
    val allScore: LiveData<List<Score>>
    private val repository: GameRepository

    init {
        val gameDb = GameDatabase.getInstance(application)
        val gameDao = gameDb.gameDao()
        repository = GameRepository(gameDao)

        allScore = repository.allScore
    }

    fun insertScore(score: Score) {
        repository.insertScore(score)
    }

    fun deleteScoreById(id: Int) {
        repository.deleteScoreById(id)
    }

    fun deleteScore(score: Score) {
        repository.deleteScore(score)
    }
}