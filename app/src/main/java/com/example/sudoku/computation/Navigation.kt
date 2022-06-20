package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
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
    private var allScore: List<Score>? = null

    fun saveGame(){
        val game = g!!
        val sudoku = toJson(s.saveGame(game.sudoku))
        val solution = toJson(game.solution)
        val mistakes = game.mistakes
        val temp = game.elapsedTime
        score.deleteScoreById(1)
        score.insertScore(Score(1, diff.value, mistakes.value, temp, sudoku, solution, game.counter.value))
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
        s.changeGame()
        g = s.getGame()
        timer.value = 0L
        val note = rememberSaveable { mutableStateOf(false) }
        if(g != null) {
            GameScreen(this, g!!, timer, score, numberScore, start, context, Action(note, 0,0))
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
        val allScore = score.allScore.observeAsState(listOf())
        if (allScore.value.isNotEmpty()) {
            for (i in allScore.value.indices) {
                if (allScore.value[i].id == 1) {
                    g = s.setGame(allScore.value[i])
                    timer.value = g!!.elapsedTime
                    this.allScore = allScore.value
                    score.deleteScore(allScore.value[i])
                    screen.value = Screen.LOADED_GAME_SCREEN
                } else {
                    val str = stringResource(R.string.no_game)
                    context.makeShortToast(str)
                }
            }
        } else {
            val str = stringResource(R.string.no_game)
            context.makeShortToast(str)
            MainScreen()
        }
    }
    @Composable
    fun OpenLoadGameScreen(){
        val note = rememberSaveable { mutableStateOf(false) }
        GameScreen(this, g!!, timer, score, numberScore, start, context, Action(note, 0, 0))
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
            Screen.LOADED_GAME_SCREEN -> { OpenLoadGameScreen() }
        }
    }
}