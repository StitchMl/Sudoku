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
    private var coroutine:  CoroutineScope? = null

    /** Start **/
    @Composable
    fun Start(){
        SplashScreen(this)
    }

    /** Main Screen **/
    @Composable
    fun MainScreen() {
        FirstScreen(this, empty, diff)
    }
    /** Game Screen **/
    @Composable
    fun NewGameScreen() {
        coroutine = rememberCoroutineScope()
        s.changeGame()
        g = s.getGame()
        timer.value = 0L
        if(g != null) {
            GameScreen(this, g!!, timer, coroutine!!, score, numberScore, context)
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
                    MainScreen()
                }
            }
            val str = stringResource(R.string.no_game)
            context.makeShortToast(str)
        } else {
            val str = stringResource(R.string.no_game)
            context.makeShortToast(str)
            MainScreen()
        }
    }
    @Composable
    fun OpenLoadGameScreen(){
        coroutine = rememberCoroutineScope()
        GameScreen(this, g!!, timer, coroutine!!, score, numberScore, context)
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
    fun saveGame() {
        val game = g!!
        val sudoku = toStr(s.saveGame(game.sudoku))
        val numb = toStr(game.numb.value)
        val solution = toStr(game.solution)
        val mistakes = game.mistakes
        val temp = timer.value
        coroutine?.cancel()

        // Cancella il vecchio salvataggio con id = 1 e salva il gioco corrente
        score.deleteScoreById(1)
        val scoreToSave = Score(
            id = 1, // Salva con id fisso per il gioco interrotto
            diff.value,
            mistakes.value,
            temp,
            numb,
            sudoku,
            solution,
            game.counter.value
        )

        CoroutineScope(Dispatchers.IO).launchPeriodicAsync(1000, scoreToSave).start()

        g = null // Libera il gioco attuale
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

    private fun toStr(matrix: IntArray): String {
        val sb = StringBuilder()
        matrix.forEachIndexed { i, value ->
                sb.append("[$i;$value],")
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