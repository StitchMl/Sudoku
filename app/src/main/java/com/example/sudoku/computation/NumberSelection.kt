package com.example.sudoku.computation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//funzione per i numeri sotto la griglia

@Composable
fun NumberSelection(
    onClicked: (Int) -> Unit
) {
    BoxWithConstraints {
        val itemSize = maxWidth / 9

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            (1..9).forEach {
                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .run {
                           clickable { onClicked(it) }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.toString(),
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                           // color = SudokuColors.NumberSelectionColor
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    NumberSelection { /*TODO*/ }
}