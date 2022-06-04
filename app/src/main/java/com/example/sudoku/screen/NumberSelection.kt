package com.example.sudoku.screen

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.computation.makeShortToast
import com.example.sudoku.model.Game
import com.example.sudoku.model.Setting

/** Create the board of number to insert **/
@Composable
fun NumberSelection(g: Game, context: Context) {
    val tempVal = rememberSaveable { mutableStateOf(false) }
    val str = stringResource(R.string.wrong)
    g.bar.bar = Array(9){tempVal}
    BoxWithConstraints {
        val itemSize = maxWidth / 9
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            (1..9).forEach {
                val clicked = rememberSaveable { mutableStateOf(false) }
                g.bar.bar[it-1] = clicked
                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .background(if (clicked.value) Color.Gray else Color.White)
                        .run { clickable { clickAction(str, it, g, context) } },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.toString(),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
    }
}

fun clickAction(str: String, it: Int, g: Game, context: Context){
    if(g.i_Select != null && g.j_Select != null){
        if(g.sudoku[g.i_Select!!][g.j_Select!!].sol == it) {
            g.sudoku[g.i_Select!!][g.j_Select!!].value?.value = it
            g.sudoku[g.i_Select!!][g.j_Select!!].click?.value = 0
            g.oneSelect = false
            g.i_Select = null
            g.j_Select = null
            g.counter.value += 1
        } else {
            context.makeShortToast(str)
        }
    } else {
        if (g.bar.select != 0) {
            g.bar.bar[g.bar.select-1]?.value = false
            g.bar.bar[it - 1]?.value = true
            g.bar.select = it
        } else {
            g.bar.bar[it - 1]?.value = true
            g.bar.select = it
        }
        for (i in 0 until g.sudoku.size) {
            for (j in 0 until g.sudoku[i].size) {
                if (g.sudoku[i][j].value?.value == it) {
                    g.sudoku[i][j].click?.value = 2
                } else {
                    g.sudoku[i][j].click?.value = 0
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    val d = rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    val set = Setting(context)
    val diff = rememberSaveable { mutableStateOf(set.DIFFICULTY[0]) }
    set.setDifficult(diff.value, d)
    val s = Sudoku(9, d, diff)
    val game = s.getGame()
    NumberSelection(game, context)
}
