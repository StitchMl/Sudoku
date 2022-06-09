package com.example.sudoku.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sudoku.model.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScoreViewModel(application: Application): ViewModel() {
    private val scoreList = MutableStateFlow(listOf<Score>())
    val realTimeUpdateItem: StateFlow<List<Score>> get() = scoreList
    val allScore: LiveData<List<Score>>
    //val allGames: LiveData<List<Game>>
    private val repository: GameRepository
    //val searchResults: MutableLiveData<List<Game>>

    init {
        val gameDb = GameDatabase.getInstance(application)
        val gameDao = gameDb?.gameDao()
        repository = GameRepository(gameDao!!)

        //allGames = repository.allGames
        allScore = repository.allScore
        //searchResults = repository.searchResults
        //getUpdateItem()
    }

    private fun getUpdateItem() {
        viewModelScope.launch(Dispatchers.Default) {
            val initialRealTimeUpdateItem = arrayListOf<Score>()
            repeat(20) {
                initialRealTimeUpdateItem += Score(
                    mistakes =  it,
                    time = it.toLong()
                )
            }
            scoreList.emit(initialRealTimeUpdateItem)
        }
    }

    /*fun insertGame(game: Game) {
        repository.insertGame(game)
    }*/

    fun insertScore(score: Score) {
        repository.insertScore(score)
        println(score.id)
        println(allScore.value)
    }

    /*fun findGame(id: Int) {
        repository.findGame(id)
    }

    fun deleteGame(id: Int) {
        repository.deleteGame(id)
    }*/
}