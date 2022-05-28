package com.example.sudoku.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun ShowNUmberSelection(){
    NumberSelection(GameState(context = LocalContext.current).missingNumbers){ /*TODO*/ }
}

@Composable
fun NumberSelection(
    missingNumbers: Set<Int>,
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
                val isMissing = it in missingNumbers

                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .run {
                            if (isMissing) clickable { onClicked(it) } else this
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (isMissing) {
                        Text(
                            text = it.toString(),
                            style = TextStyle(
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                    }
                }
            }
        }
    }
}