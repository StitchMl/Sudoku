package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Game
import com.example.sudoku.screen.*
import java.util.*

class Navigation(
    private val empty: MutableState<Int>,
    private val diff: MutableState<String>,
    private val timer: MutableState<Long>,
    private val newRecord: MutableState<Boolean>,
    private val screen: MutableState<Screen>,
    private val start: MutableState<Boolean>,
    private val allGames: List<Game>,
    private val score: ScoreViewModel,
    private val context: Context
) {
    private var g: Game? = null
    private val s: Sudoku = Sudoku(9, empty, diff)

    init {
        val t = Timer()
        val task = MyTimerTask(timer, start)
        t.scheduleAtFixedRate(task, 1000, 1000)
    }

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
        NewGameScreen(this, g!!, timer, newRecord, start, score, context)
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
            Screen.NEW_GAME_SCREEN -> { NewGameScreen() }
            Screen.VICTORY -> { Victory() }
            Screen.FAIL -> { Fail() }
            Screen.LOAD_GAME_SCREEN -> { LoadGameScreen() }
            Screen.RULES_SCREEN -> { LoadRulesScreen() }
            Screen.RESULT_SCREEN -> { LoadScoreScreen() }
        }
    }
}