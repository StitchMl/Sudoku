package com.example.sudoku.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.R
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.computation.makeLongToast
import com.example.sudoku.model.Game
import com.example.sudoku.model.Setting

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
        val (sudoku, number) = createRefs()

        Box(modifier = Modifier.constrainAs(sudoku){
            top.linkTo(parent.top, margin = 8.dp)
        }) {
            CreateBoard(g)
        }
        Box(modifier = Modifier.constrainAs(number){
            top.linkTo(sudoku.bottom, margin = 10.dp)
        }) {
            NumberSelection(g, context)
        }
    }
    if (g.counter.value == (9*9)){
        val str = stringResource(R.string.won)
        context.makeLongToast(str)
        navController.navigate("main_screen")
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
    Column {
        Text(
            "${diff.value} ${k.value}",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        NewGameScreen(rememberNavController(), s.getGame(), t, b, context)
    }
}
