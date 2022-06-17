package com.example.sudoku.screen

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sudoku.R
import com.example.sudoku.computation.*
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Action
import com.example.sudoku.model.Game
import com.example.sudoku.model.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


@Composable
fun GameScreen(navController: Navigation, g: Game, timer: MutableState<Long>,
               newRecord: MutableState<Boolean>, model: ScoreViewModel, numberScore:  MutableState<Int>,
               start: MutableState<Boolean>, context: Context, action: Action
) {
    setScreenGame(navController, g, timer, model, numberScore, start, context, action)
}

@Composable
fun setScreenGame(navController: Navigation, game: Game,
                  timer: MutableState<Long>, model: ScoreViewModel, numberScore: MutableState<Int>,
                  start: MutableState<Boolean>, context: Context, action: Action
){
    //TITLE
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.sudoku), fontSize = 70.sp)
        Spacer(modifier = Modifier.size(30.dp))

        ConstraintLayout {
            // Create references for the composable to constrain
            val (sudoku, number, infoBar, actionBar) = createRefs()

            Box(modifier = Modifier.constrainAs(sudoku) {
                top.linkTo(infoBar.bottom, margin = 8.dp)
            }) {
                CreateBoard(game)
            }
            Box(modifier = Modifier.constrainAs(actionBar) {
                top.linkTo(number.bottom, margin = 10.dp)
            }) {
                GameActionBar(game, context, action)
            }
            Box(modifier = Modifier.constrainAs(number) {
                top.linkTo(sudoku.bottom, margin = 10.dp)
            }) {
                NumberSelection(game, context, action)
            }
            Box(modifier = Modifier.constrainAs(infoBar) {
                top.linkTo(parent.top, margin = 10.dp)
            }) {
                CurrentInfoBar(game, timer)
            }
        }
        start.value = true
        val t = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(1000, timer, start)
        println(timer.value.toTime())
        when (game.counter.value) {
            81 -> {
                model.deleteScoreById(1)
                t.cancel()
                start.value = false
                val str = stringResource(R.string.won)
                game.elapsedTime = timer.value
                context.makeShortToast(str)
                model.insertScore(Score(numberScore.value, game.difficult, game.mistakes.value, game.elapsedTime))
                numberScore.value++
                navController.setScreen(Screen.VICTORY)
            }
            0 -> {
                model.deleteScoreById(1)
                t.cancel()
                start.value = false
                val str = stringResource(R.string.game_over)
                game.elapsedTime = timer.value
                context.makeShortToast(str)
                navController.setScreen(Screen.FAIL)
            }
        }
    }
}

fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    timer: MutableState<Long>,
    start: MutableState<Boolean>
) = this.async {
    if (repeatMillis > 0 && start.value) {
        while (start.value) {
            timer.value++
            delay(repeatMillis)
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun NewGameScreenPreview(){
    val context = LocalContext.current
    val set = Setting(context)
    val k = rememberSaveable { mutableStateOf(0) }
    val empty = rememberSaveable { mutableStateOf(0) }
    val numberScore = rememberSaveable { mutableStateOf(1) }
    val t = rememberSaveable { mutableStateOf(0L) }
    val b = rememberSaveable { mutableStateOf(false) }
    val screen = rememberSaveable { mutableStateOf(Screen.SPLASH_SCREEN) }
    val timer = rememberSaveable{ mutableStateOf(0L) }
    val newRecord = rememberSaveable{ mutableStateOf(false) }
    val start = rememberSaveable{ mutableStateOf(false) }
    val diff = rememberSaveable { mutableStateOf(set.DIFFICULTY[1]) }
    set.setDifficult(diff.value, k)
    val s = Sudoku(9, k, diff)
    val noteBool = rememberSaveable { mutableStateOf(false) }
    val score = ScoreViewModel(LocalContext.current.applicationContext as Application)
    GameScreen(Navigation(empty, diff, timer, newRecord, screen,
        score, numberScore, start, context), s.getGame(), t, b, score, numberScore,
        start, context, Action(noteBool, 0, 0)
    )
}
