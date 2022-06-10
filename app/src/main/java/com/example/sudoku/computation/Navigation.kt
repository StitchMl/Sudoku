package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.example.sudoku.R
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Game
import com.example.sudoku.model.SavedCell
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.screen.*

class Navigation(
    private val empty: MutableState<Int>,
    private val diff: MutableState<String>,
    private val timer: MutableState<Long>,
    private val newRecord: MutableState<Boolean>,
    private val screen: MutableState<Screen>,
    private val score: ScoreViewModel,
    private val start: MutableState<Boolean>,
    private val context: Context
) {
    private var g: Game? = null
    private var s: Sudoku = Sudoku(9, empty, diff)

    fun saveGame(){
        val sudoku = s.saveGame(g?.sudoku!!)
        val solution = g!!.solution
        s.changeGame()
        for (i in sudoku.indices){
            for (j in 0 until sudoku[i].size){
                score.insertCell(SavedCell(i, j, solution[i][j], sudoku[i][j]))
            }
        }
        score.insertSudoku(SavedSudoku(g!!.difficult, g!!.mistakes, g!!.elapsedTime))
        g = null
    }

    @Composable
    fun Start(){
        SplashScreen(this)
    }

    // Main Screen
    @Composable
    fun MainScreen() {
        timer.value = 0L
        FirstScreen(this, empty, diff)
    }
    // Game Screen
    @Composable
    fun NewGameScreen() {
        g = s.getGame()
        timer.value = 0L
        if(g != null) {
            GameScreen(this, g!!, timer, newRecord, score, start, context)
        }
    }
    @Composable
    fun Victory() {
        g = null
        VictoryScreen(this, timer, newRecord)
    }
    @Composable
    fun Fail() {
        g = null
        FailureScreen(this, timer, newRecord)
    }
    @Composable
    fun LoadGameScreen(){
        val allSudoku by score.allSudoku.observeAsState(listOf())
        val allCell by score.allCell.observeAsState(listOf())
        if (allSudoku.isNotEmpty() && allCell.isNotEmpty()) {
            g = s.setGame(allSudoku[0], allCell)
            timer.value = allSudoku[0].time
            score.deleteSudoku(allSudoku[0].id)
            GameScreen(this, g!!, timer, newRecord, score, start, context)
        } else {
            val str = stringResource(R.string.no_game)
            context.makeShortToast(str)
            screen.value = Screen.MAIN_SCREEN
        }
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