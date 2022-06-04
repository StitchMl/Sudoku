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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.R
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.computation.makeShortToast
import com.example.sudoku.model.Game
import com.example.sudoku.model.Setting
import java.util.*


@Composable
fun NewGameScreen(
    navController: NavHostController,
    g: Game,
    timer: MutableState<Long>,
    newRecord: MutableState<Boolean>,
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
            CurrentInfoBar(g, timer, context)
        }
    }
    val t = Timer()
    t.scheduleAtFixedRate(object : TimerTask() {
        override fun run() {
            timer.value++
        }
    }, 1000, 1000)
    if (g.counter.value == (9*9)){
        val str = stringResource(R.string.won)
        g.elapsedTime = timer.value
        context.makeShortToast(str)
        navController.navigate("victory")
    } else if (g.counter.value == 0){
        val str = stringResource(R.string.game_over)
        g.elapsedTime = timer.value
        context.makeShortToast(str)
        navController.navigate("fail")
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun NewGameScreenPreview(){
    val context = LocalContext.current
    val set = Setting(context)
    val k = rememberSaveable { mutableStateOf(0) }
    val t = rememberSaveable { mutableStateOf(0L) }
    val b = rememberSaveable { mutableStateOf(false) }
    val diff = rememberSaveable { mutableStateOf(set.DIFFICULTY[1]) }
    set.setDifficult(diff.value, k)
    val s = Sudoku(9, k, diff)
    NewGameScreen(rememberNavController(), s.getGame(), t, b, context)
}
