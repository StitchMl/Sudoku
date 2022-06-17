package com.example.sudoku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.model.Game

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun ShowNUmberSelection(){
    val d = rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    val set = Setting(context)
    val diff = rememberSaveable { mutableStateOf(set.DIFFICULTY[0]) }
    set.setDifficult(diff.value, d)
    val s = Sudoku(9, d, diff)
    val game = s.getGame()
    Column {
        Text(
            "${diff.value} ${d.value}",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        CreateBoard(game)
    }
}

@Composable
fun CreateBoard(
    game: Game
) {
    BoxWithConstraints {
        val itemSize = (maxWidth-8.dp) / 9

        Column(modifier = Modifier.padding(4.dp)) {
            (0..2).forEach { n ->
                Row(modifier = Modifier.border(1.dp, Color.Black)
                ) { (0..2).forEach { m ->
                        Column(modifier = Modifier
                                .border(1.dp, Color.Black)
                        ) { (0..2).forEach { i ->
                                Row { (0..2).forEach { j ->
                                    val row = i + (n * 3)
                                    val col = j + (m * 3)
                                    CreateCell(row, col, game, itemSize) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun cellSelect(i: Int, j: Int, g: Game){
    if(g.sudoku[i][j].click?.value == 1){
        g.jSelect = null
        g.iSelect = null
        g.oneSelect = false
        g.sudoku[i][j].click?.value = 0
    } else if (g.oneSelect){
        g.sudoku[g.iSelect!!][g.jSelect!!].click?.value = 0
        g.iSelect = i
        g.jSelect = j
        g.sudoku[i][j].click?.value = 1
    } else {
        g.oneSelect = true
        g.iSelect = i
        g.jSelect = j
        for (row in 0 until g.sudoku.size){
            for (col in 0 until g.sudoku[row].size){
                    g.sudoku[row][col].click?.value = 0
            }
        }
        g.sudoku[i][j].click?.value = 1
        if (g.bar.select != 0) {
            g.bar.bar[g.bar.select-1]?.value = false
            g.bar.select = 0
        }
    }
}

@Composable
fun CreateCell(row:Int, col:Int, game:Game, itemSize: Dp){
    val click = rememberSaveable { mutableStateOf(0) }
    game.sudoku[row][col].click = click
    Box(
        modifier = Modifier
            .border(1.dp, Color.LightGray)
            .background(if (click.value == 1) Color.Gray else if (click.value == 2) Color.LightGray else Color.White)
            .size(itemSize)
            .run {
                if (game.sudoku[row][col].value?.value == 0) clickable {
                    cellSelect(row, col, game)
                } else this
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (game.sudoku[row][col].value?.value != 0) game.sudoku[row][col].value?.value.toString() else if (game.sudoku[row][col].note?.value != 0) game.sudoku[row][col].note?.value.toString() else "",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = if (game.sudoku[row][col].value?.value != 0) Color.Black else Color.LightGray
            )
        )
    }
}
