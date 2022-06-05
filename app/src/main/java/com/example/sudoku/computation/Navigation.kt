package com.example.sudoku.computation

import android.content.Context
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.sudoku.model.Game
import com.example.sudoku.model.ScoreViewModel
import com.example.sudoku.screen.*

class Navigation(
    private val s: Sudoku, private val empty: MutableState<Int>, private val diff: MutableState<String>,
    private val timer: MutableState<Long>, private val newRecord: MutableState<Boolean>,
    private val screen: MutableState<Screen>, private val start: MutableState<Boolean>, private val context: Context
) {
    private var g: Game? = null
    private val score: ScoreViewModel by viewModels()

    @Composable
    fun Start(){
        SplashScreen(this)
    }

    // Main Screen
    @Composable
    fun MainScreen() {
        timer.value = 0L
        FirstScreen(this, empty, diff, s)
    }
    // Game Screen
    @Composable
    fun NewGameScreen() {
        g = s.getGame()
        timer.value = 0L
        NewGameScreen(this, g!!, timer, newRecord, start, context)
    }
    @Composable
    fun Victory() {
        VictoryScreen(this, timer, newRecord)
    }
    @Composable
    fun Fail() {
        FailureScreen(this, timer, newRecord)
    }
    @Composable
    fun LoadGameScreen(){
        /*TODO LoadGameScreen(this)*/
    }
    // Rules Screen
    @Composable
    fun LoadRulesScreen(){
        RulesScreen()
    }
    // Result Screen
    @Composable
    fun LoadScoreScreen() {
        ScoreScreen(score)
    }

    fun setScreen(n: Screen){
        screen.value = n
    }

    @Composable
    fun GetScreen() {
        return when (screen.value) {
            Screen.SPLASH_SCREEN -> { Start() }
            Screen.MAIN_SCREEN -> { MainScreen() }
            Screen.NEW_GAME_SCREEN -> {
                NewGameScreen()
            }
            Screen.VICTORY -> { Victory() }
            Screen.FAIL -> { Fail() }
            Screen.LOAD_GAME_SCREEN -> { LoadGameScreen() }
            Screen.RULES_SCREEN -> { LoadRulesScreen() }
            Screen.RESULT_SCREEN -> { ScoreScreen(score) }
        }
    }
}