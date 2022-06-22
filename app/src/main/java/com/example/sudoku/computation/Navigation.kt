package com.example.sudoku.computation

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import com.example.sudoku.R
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Game
import com.example.sudoku.model.Score
import com.example.sudoku.screen.*
import kotlinx.coroutines.*

class Navigation(
    private val empty: MutableState<Int>,
    private val diff: MutableState<String>,
    private val timer: MutableState<Long>,
    private val newRecord: MutableState<Boolean>,
    private val screen: MutableState<Screen>,
    private val score: ScoreViewModel,
    private val numberScore: MutableState<Int>,
    private val context: Context
) {
    private var g: Game? = null
    private var s: Sudoku = Sudoku(9, empty, diff)
    private var allScore: List<Score>? = null
    private var coroutine:  MutableState<CoroutineScope>? = null

    /** Start **/
    @Composable
    fun Start(){
        SplashScreen(this)
    }

    /** Main Screen **/
    @Composable
    fun MainScreen() {
        timer.value = 0L
        FirstScreen(this, empty, diff)
    }
    /** Game Screen **/
    @Composable
    fun NewGameScreen() {
        s.changeGame()
        g = s.getGame()
        timer.value = 0L
        coroutine = remember{ mutableStateOf(CoroutineScope(Dispatchers.IO)) }
        if(g != null) {
            GameScreen(this, g!!, timer, coroutine!!.value, score, numberScore, context)
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
                    coroutine = remember{ mutableStateOf(CoroutineScope(Dispatchers.IO)) }
                    g = s.setGame(allScore.value[i])
                    println(g!!.elapsedTime)
                    timer.value = g!!.elapsedTime
                    println(timer.value)
                    this.allScore = allScore.value
                    println(timer.value)
                    score.deleteScore(allScore.value[i])
                    println(timer.value)
                    screen.value = Screen.LOADED_GAME_SCREEN
                } else {
                    val str = stringResource(R.string.no_game)
                    context.makeShortToast(str)
                    MainScreen()
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
        GameScreen(this, g!!, timer, coroutine!!.value, score, numberScore, context)
    }
    /** Rules Screen **/
    @Composable
    fun LoadRulesScreen(){
        RulesScreen()
    }
    /** Result Screen **/
    @Composable
    fun LoadScoreScreen() {
        ScoreScreen(score)
    }
    /** Save Game **/
    fun saveGame(){
        val game = g!!
        val sudoku = toStr(s.saveGame(game.sudoku))
        val solution = toStr(game.solution)
        val mistakes = game.mistakes
        val temp = timer.value
        coroutine?.value?.cancel()
        score.deleteScoreById(1)
        val scoreToSave = Score(1, diff.value, mistakes.value, temp, sudoku, solution, game.counter.value)
        CoroutineScope(Dispatchers.IO).launchPeriodicAsync(1000, scoreToSave).start()
        g = null
    }
    private fun CoroutineScope.launchPeriodicAsync(
        repeatMillis: Long,
        game: Score
    ) = this.async {
        if (repeatMillis > 0) {
            while (score.searchResults.value == null) {
                score.insertScore(game)
                delay(repeatMillis)
                async{ score.findScoreById(1) }.start()
            }
            this.cancel()
        }
    }
    private fun toStr(matrix: Array<IntArray>): String {
        val sb = StringBuilder()
        matrix.forEachIndexed { i, row ->
            row.forEachIndexed{ j, value->
                sb.append("[$i;$j;$value],")
            }
        }
        return "$sb"
    }

    /** Set Screen **/
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