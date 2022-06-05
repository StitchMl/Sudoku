package com.example.sudoku.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
class Score (
    var id: Int = 0,
    var mistakes: Int = 0,
    var time: Int = 0
): Parcelable

class ScoreViewModel: ViewModel() {
    private val scoreList = MutableStateFlow(listOf<Score>())
    val realTimeUpdateItem: StateFlow<List<Score>> get() = scoreList

    init {
        getUpdateItem()
    }

    private fun getUpdateItem() {
        viewModelScope.launch(Dispatchers.Default) {
            val initialRealTimeUpdateItem = arrayListOf<Score>()
            repeat(20) {
                initialRealTimeUpdateItem += Score(
                    id = it + 1,
                    mistakes =  it,
                    time = it
                )
            }
            scoreList.emit(initialRealTimeUpdateItem)
        }
    }
}
