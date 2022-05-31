package com.example.sudoku.screen

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import com.example.sudoku.computation.Sudoku
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
        CreateBoard(s.get()) { /*TODO*/ }
    }
}

@Composable
fun CreateBoard(
    sudoku: Array<IntArray>,
    onClicked: (Int) -> Unit
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
                                        val isMissing = sudoku[i + (n * 3)][j + (m * 3)]
                                        Box(
                                            modifier = Modifier
                                                .border(
                                                    1.dp,
                                                    Color.LightGray
                                                )
                                                .size(itemSize)
                                                .run {
                                                    if (isMissing == 0) clickable { onClicked(j) } else this
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
