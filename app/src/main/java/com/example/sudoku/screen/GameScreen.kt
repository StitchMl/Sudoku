package com.example.sudoku.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.sudoku.R
import com.example.sudoku.computation.*
import com.example.sudoku.model.Game


@Composable
fun NewGameScreen(
    navController: Navigation,
    g: Game,
    timer: MutableState<Long>,
    newRecord: MutableState<Boolean>,
    start: MutableState<Boolean>,
    context: Context
){
    ConstraintLayout {
        // Create references for the composable to constrain
        val (sudoku, number,infoBar) = createRefs()

        Box(modifier = Modifier.constrainAs(sudoku){
            top.linkTo(infoBar.bottom, margin = 8.dp)
        }) {
            CreateBoard(g)
        }
        Box(modifier = Modifier.constrainAs(number){
            top.linkTo(sudoku.bottom, margin = 10.dp)
        }) {
            NumberSelection(g, context)
        }
        Box(modifier = Modifier.constrainAs(infoBar){
            top.linkTo(parent.top, margin = 10.dp)
        }) {
            CurrentInfoBar(g, timer)
        }
    }
    start.value = true
    if (g.counter.value == (9*9)){
        start.value = false
        val str = stringResource(R.string.won)
        g.elapsedTime = timer.value
        context.makeShortToast(str)
        navController.setScreen(Screen.VICTORY)
    } else if (g.counter.value == 0){
        start.value = false
        val str = stringResource(R.string.game_over)
        g.elapsedTime = timer.value
        context.makeShortToast(str)
        navController.setScreen(Screen.FAIL)
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun NewGameScreenPreview(){
    val context = LocalContext.current
    val set = Setting(context)
    val k = rememberSaveable { mutableStateOf(0) }
    val empty = rememberSaveable { mutableStateOf(0) }
    val t = rememberSaveable { mutableStateOf(0L) }
    val b = rememberSaveable { mutableStateOf(false) }
    val start = rememberSaveable { mutableStateOf(false) }
    val screen = rememberSaveable { mutableStateOf(Screen.SPLASH_SCREEN) }
    val timer = rememberSaveable{ mutableStateOf(0L) }
    val newRecord = rememberSaveable{ mutableStateOf(false) }
    val diff = rememberSaveable { mutableStateOf(set.DIFFICULTY[1]) }
    set.setDifficult(diff.value, k)
    val s = Sudoku(9, k, diff)
    NewGameScreen(Navigation(s, empty, diff, timer, newRecord, screen, start, context),
        s.getGame(), t, b, start, context)
}
