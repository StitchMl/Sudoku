package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.example.sudoku.model.Game
import com.example.sudoku.screen.*

class Navigation(
    private val s: Sudoku, private val empty: MutableState<Int>, private val diff: MutableState<String>,
    private val timer: MutableState<Long>, private val newRecord: MutableState<Boolean>,
    private val screen: MutableState<Int>, private val start: MutableState<Boolean>, private val context: Context
) {
    private var g: Game? = null

    /** 00 */
    @Composable
    fun Start(){
        SplashScreen(this)
    }

    // Main Screen
    /** 01 */
    @Composable
    fun Main_screen() {
        timer.value = 0L
        FirstScreen(this, empty, diff, s)
    }
    // Game Screen
    /** 02 */
    @Composable
    fun New_game_screen() {
        g = s.getGame()
        timer.value = 0L
        NewGameScreen(this, g!!, timer, newRecord, start, context)
    }
    /** 03 */
    @Composable
    fun Victory() {
        VictoryScreen(this, timer, newRecord)
    }
    /** 04 */
    @Composable
    fun Fail() {
        FailureScreen(this, timer, newRecord)
    }
    /** 05 */
    @Composable
    fun Load_game_screen(){
        /*TODO LoadGameScreen(this)*/
    }
    // Rules Screen
    /** 06 */
    @Composable
    fun Rules_screen(){
        RulesScreen()
    }
    // Result Screen
    /** 07 */
    @Composable
    fun Result_screen() {
        /*TODO ResultScreen(this)*/
    }

    fun setScreen(n: Int){
        screen.value = n
    }

    fun getScreen(): Int{
        return screen.value
    }
}