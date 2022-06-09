package com.example.sudoku.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sudoku.model.Score

class ScoreViewModel(application: Application): ViewModel() {
    //private val scoreList = MutableStateFlow(listOf<Score>())
    //val realTimeUpdateItem: StateFlow<List<Score>> get() = scoreList
    //val allGames: LiveData<List<Game>>
    //val searchResults: MutableLiveData<List<Game>>
    val allScore: LiveData<List<Score>>
    private val repository: GameRepository
    val searchScoreResults: MutableLiveData<List<Score>>

    init {
        val gameDb = GameDatabase.getInstance(application)
        val gameDao = gameDb.gameDao()
        repository = GameRepository(gameDao)

        allScore = repository.allScore
        searchScoreResults = repository.searchScoreResults

        //allGames = repository.allGames
        //searchResults = repository.searchResults
        //getUpdateItem()
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

    /*private fun getUpdateItem() {
        viewModelScope.launch(Dispatchers.Default) {
            val initialRealTimeUpdateItem = arrayListOf<Score>()
            repeat(20) {
                initialRealTimeUpdateItem += Score(
                    id = it + 1,
                    diff = "",
                    mistakes =  it,
                    time = it.toLong()
                )
            }
            scoreList.emit(initialRealTimeUpdateItem)
        }
    }*/

    /*fun insertGame(game: Game) {
        repository.insertGame(game)
    }*/

    /*fun findGame(id: Int) {
        repository.findGame(id)
    }

    fun deleteGame(id: Int) {
        repository.deleteGame(id)
    }*/
}