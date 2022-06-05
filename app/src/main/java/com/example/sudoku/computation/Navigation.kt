package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.sudoku.model.Game
import com.example.sudoku.screen.*

class Navigation(
    private val s: Sudoku, private val empty: MutableState<Int>, private val diff: MutableState<String>,
    private val timer: MutableState<Long>, private val newRecord: MutableState<Boolean>,
    private val screen: MutableState<Screen>, private val start: MutableState<Boolean>, private val context: Context
) {
    private var g: Game? = null

    @Composable
    fun Start(){
        SplashScreen(this)
    }

    // Main Screen
    @Composable
    fun Main_screen() {
        timer.value = 0L
        FirstScreen(this, empty, diff, s)
    }
    // Game Screen
    @Composable
    fun New_game_screen() {
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
    fun Load_game_screen(){
        /*TODO LoadGameScreen(this)*/
    }
    // Rules Screen
    @Composable
    fun Rules_screen(){
        RulesScreen()
    }
    // Result Screen
    @Composable
    fun Result_screen() {
        /*TODO ResultScreen(this)*/
    }

    fun setScreen(n: Screen){
        screen.value = n
    }

    @Composable
    fun GetScreen() {
        return when (screen.value) {
            Screen.SPLASH_SCREEN -> { Start() }
            Screen.MAIN_SCREEN -> { Main_screen() }
            Screen.NEW_GAME_SCREEN -> { New_game_screen() }
            Screen.VICTORY -> { Victory() }
            Screen.FAIL -> { Fail() }
            Screen.LOAD_GAME_SCREEN -> { Load_game_screen() }
            Screen.RULES_SCREEN -> { Rules_screen() }
            Screen.RESULT_SCREEN -> { Result_screen() }
        }
    }
}