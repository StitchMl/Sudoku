package com.example.sudoku.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.model.Game

//funzione per i numeri sotto la griglia

@Composable
fun NumberSelection(g: Game, context: Context) {
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
                           clickable {
                               if(g.solution[g.i_Select!!][g.j_Select!!] == it) {
                                   g.sudoku[g.i_Select!!][g.j_Select!!].mutableValue?.value = it
                                   g.sudoku[g.i_Select!!][g.j_Select!!].value = it
                               } else {
                                   Toast.makeText(context,
                                       "Sbagliato",
                                       Toast.LENGTH_SHORT).show()
                               }
                           }
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

/*
@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    NumberSelection()
}*/
