package com.example.sudoku.screen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.sudoku.computation.Sudoku

@Composable
fun NewGameScreen(
    navController: NavHostController,
    empty: MutableState<Int>,
    diff: MutableState<String>,
    context: Context
){
    val s = Sudoku(9, empty.value)
    val g = s.getGame()
    g.difficult = diff.value
    s.printSudoku()
    ConstraintLayout {
        // Create references for the composable to constrain
        val (sudoku, number) = createRefs()

        Box(modifier = Modifier.constrainAs(sudoku){
            top.linkTo(parent.top, margin = 8.dp)
        }) {
            CreateBoard(g.sudoku, g)
        }
        Box(modifier = Modifier.constrainAs(number){
            top.linkTo(sudoku.bottom, margin = 10.dp)
        }) {
            NumberSelection(g, context)
        }
    }
}

/*
@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun NewGameScreenPreview(){
    val empty = rememberSaveable { mutableStateOf(20) }
    val diff = rememberSaveable { mutableStateOf("") }
    NewGameScreen(rememberNavController(), empty, diff, context)
}*/
