package com.example.sudoku.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavHostController
import com.example.sudoku.computation.Sudoku
import kotlinx.coroutines.NonDisposableHandle.parent

@Composable
fun NewGameScreen(navController: NavHostController, diff: MutableState<Int>){
    val s = Sudoku(9, diff.value)
    s.fillValues()
    ConstraintLayout {
        // Create references for the composable to constrain
        val (sudoku) = createRefs()

        Box(modifier = Modifier.constrainAs(sudoku){
            top.linkTo(parent.top, margin = 8.dp)
        }) {
            CreateBoard(s.get()){ /*TODO*/ }
        }
    }
}