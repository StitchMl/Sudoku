package com.example.sudoku.model

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.random.Random

@Parcelize
class Score (
    var id: Int = 0,
    var mistakes: Int = 0,
    var time: Int = 0
): Parcelable

class ScoreViewModel: ViewModel() {
    private val scorelist = MutableStateFlow(listOf<Score>())
    val realTimeUpdateItem: StateFlow<List<Score>> get() = scorelist

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
            scorelist.emit(initialRealTimeUpdateItem)
        }
    }
}
