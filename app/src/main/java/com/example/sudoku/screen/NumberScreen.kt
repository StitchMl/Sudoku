package com.example.sudoku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.model.Cell
import com.example.sudoku.model.Game
import com.example.sudoku.model.Setting

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun ShowNUmberSelection(){
    val d = rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    val diff = context.resources.getStringArray(
        R.array.difficulty)[3]
    Setting(context).setDifficult(diff, d)
    val s = Sudoku(9, d.value)
    s.fillValues()
    Column {
        Text(
            "$diff ${d.value}",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        CreateBoard(s.get(), Game(diff,s.get(),s.getSolution()))
    }
}

@Composable
fun CreateBoard(
    sudoku: Array<Array<Cell>>,
    game: Game
) {
    BoxWithConstraints {
        val itemSize = maxWidth / 9

        Column(modifier = Modifier.padding(4.dp)) {
            (0..2).forEach { n ->
                Row(
                    modifier = Modifier
                        .border(1.dp, Color.Black)
                ) {
                    (0..2).forEach { m ->
                        Column(
                            modifier = Modifier
                                .border(1.dp, Color.Black)
                        ) {
                            (0..2).forEach { i ->
                                Row {
                                    (0..2).forEach { j ->
                                        val row = i + (n * 3)
                                        val col = j + (m * 3)
                                        val isMissing = sudoku[row][col].value
                                        sudoku[row][col].click = remember { mutableStateOf(false) }
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    Color.LightGray
                                                )
                                                .background(if (sudoku[row][col].click?.value!!) Color.Gray else Color.White)
                                                .size(itemSize)
                                                .run {
                                                    if (isMissing == 0) clickable {
                                                        cellSelect(sudoku, row, col, game)
                                                    } else this
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (isMissing != 0) {
                                                Text(
                                                    text = isMissing.toString(),
                                                    style = TextStyle(
                                                        fontSize = 22.sp,
                                                        fontWeight = FontWeight.Medium,
                                                        color = Color.Black
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun cellSelect(sudoku: Array<Array<Cell>>, i: Int, j: Int, g: Game){
    if(sudoku[i][j].click?.value!!){
        g.j_Select = null
        g.i_Select = null
        g.oneSelect = false
        sudoku[i][j].click?.value = false
    } else if (g.oneSelect){
        sudoku[g.i_Select!!][g.j_Select!!].click?.value = false
        g.i_Select = i
        g.j_Select = j
        sudoku[i][j].click?.value = true
    } else {
        g.oneSelect = true
        g.i_Select = i
        g.j_Select = j
        sudoku[i][j].click?.value = true
    }
}
