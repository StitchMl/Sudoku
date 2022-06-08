package com.example.sudoku.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku.R
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.model.Game
@Composable
fun GameActionBar(game: Game, context: Context) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancella),
                        contentDescription = "Erase",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "Cancella")
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))

            IconButton(onClick = { /*TODO*/ }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.appunti),
                        contentDescription = "Erase",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "Appunti")
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            IconButton(onClick = { /*TODO*/ }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.suggerimenti),
                        contentDescription = "Erase",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "Suggerimento")
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            IconButton(onClick = { /*TODO*/ }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.torna_indietro),
                        contentDescription = "Erase",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(text = "Annulla")
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun ButtonActionPreview(){
    val context = LocalContext.current
    val set = Setting(context)
    val k = rememberSaveable { mutableStateOf(0) }
    val diff = rememberSaveable { mutableStateOf(set.DIFFICULTY[1]) }
    set.setDifficult(diff.value, k)
    val s = Sudoku(9, k, diff)
    GameActionBar(s.getGame(), context)
}