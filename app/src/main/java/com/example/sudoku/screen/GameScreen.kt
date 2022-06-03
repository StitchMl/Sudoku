package com.example.sudoku.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
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
import com.example.sudoku.model.Cell
import com.example.sudoku.model.Game
import com.example.sudoku.model.NumberBar

@Composable
fun NewGameScreen(
    navController: NavHostController,
    g: Game,
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
        Toast.makeText(context, str,
            Toast.LENGTH_SHORT).show()
        navController.navigate("main_screen")
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun NewGameScreenPreview(){
    val counter = rememberSaveable { mutableStateOf(81) }
    val diff = rememberSaveable { mutableStateOf("") }
    NewGameScreen(rememberNavController(), Game(diff,
        Array(9){ Array(9){ Cell(0,0,0,null, null) } },
        NumberBar(), counter = counter), LocalContext.current)
}
