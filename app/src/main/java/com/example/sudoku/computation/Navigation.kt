package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import com.example.sudoku.R
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Action
import com.example.sudoku.model.Game
import com.example.sudoku.model.Score
import com.example.sudoku.screen.*

class Navigation(
    private val empty: MutableState<Int>,
    private val diff: MutableState<String>,
    private val timer: MutableState<Long>,
    private val newRecord: MutableState<Boolean>,
    private val screen: MutableState<Screen>,
    private val score: ScoreViewModel,
    private val numberScore: MutableState<Int>,
    private val start: MutableState<Boolean>,
    private val context: Context
) {
    private var g: Game? = null
    private var s: Sudoku = Sudoku(9, empty, diff)

    fun saveGame(){
        val game = g!!
        val sudoku = toJson(s.saveGame(game.sudoku))
        val solution = toJson(game.solution)
        val mistakes = game.mistakes
        val temp = game.elapsedTime
        s.changeGame()
        score.insertScore(Score(0, diff.value, mistakes, temp, sudoku, solution))
        g = null
    }

    private fun toJson(matrix: Array<IntArray>): String {
        val sb = StringBuilder()
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed{ j, value->
                sb.append("[$i;$j;$value],")
            }
        }
        return "$sb"
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
        val note = rememberSaveable { mutableStateOf(false) }
        if(g != null) {
            GameScreen(this, g!!, timer, newRecord, score, numberScore, start, context, Action(note, 0,0))
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
        val allScore = getScore(score)
        val note = rememberSaveable { mutableStateOf(false) }
        println(allScore)
        if (allScore.isNotEmpty()) {
            g = s.setGame(allScore[0])
            timer.value = allScore[0].time
            score.deleteScore(0)
            GameScreen(this, g!!, timer, newRecord, score, numberScore, start, context, Action(note, 0,0))
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