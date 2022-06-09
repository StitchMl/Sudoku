package com.example.sudoku.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sudoku.model.Score

class ScoreViewModel(application: Application): ViewModel() {
    val allScore: LiveData<List<Score>>
    private val repository: GameRepository
    val searchScoreResults: MutableLiveData<List<Score>>

    init {
        val gameDb = GameDatabase.getInstance(application)
        val gameDao = gameDb.gameDao()
        repository = GameRepository(gameDao)

        allScore = repository.allScore
        searchScoreResults = repository.searchScoreResults
    }

    fun insertScore(score: Score) {
        repository.insertScore(score)
        println(score.id)
        println(allScore.value)
    }

    fun findScore(diff: String) {
        repository.findScore(diff)
    }

    fun deleteScoreDiff(diff: String) {
        repository.deleteScoreDiff(diff)
    }
}