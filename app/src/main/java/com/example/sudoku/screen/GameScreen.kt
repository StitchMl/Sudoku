package com.example.sudoku.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.computation.NumberSelection
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.model.Game

@Composable
fun NewGameScreen(navController: NavHostController, empty: MutableState<Int>, diff: MutableState<String>){
    val s = Sudoku(9, empty.value)
    s.fillValues()
    val g = Game(diff.value, s.get(), s.getSolution())
    ConstraintLayout {
        // Create references for the composable to constrain
        val (sudoku, number) = createRefs()

        Box(modifier = Modifier.constrainAs(sudoku){
            top.linkTo(parent.top, margin = 8.dp)
        }) {
            CreateBoard(g.sudoku)
        }
        Box(modifier = Modifier.constrainAs(number){
            top.linkTo(sudoku.bottom, margin = 10.dp)
        }) {
            NumberSelection{ /*TODO*/ }
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun NewGameScreenPreview(){
    val empty = rememberSaveable { mutableStateOf(20) }
    val diff = rememberSaveable { mutableStateOf("") }
    NewGameScreen(rememberNavController(), empty, diff)
}