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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku.R
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.computation.makeShortToast
import com.example.sudoku.model.Action
import com.example.sudoku.model.Game


@Composable
fun GameActionBar(game: Game, context: Context, action: Action) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { eraseField(game) }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancella),
                        contentDescription = "Delete",
                        modifier = Modifier.size(24.dp)
                    )
                    val str = stringResource(R.string.delete)
                    Text(text = str)
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))

            IconButton(onClick = { action.note.value = true }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.appunti),
                        contentDescription = "Note",
                        modifier = Modifier.size(24.dp)
                    )
                    val str = stringResource(R.string.note)
                    Text(text = str)
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            IconButton(onClick = { getClue(game, context) }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.suggerimenti),
                        contentDescription = "Clue",
                        modifier = Modifier.size(24.dp)
                    )
                    val str = stringResource(R.string.clue)
                    Text(text = str)
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            IconButton(onClick = { /* UndoField(game,) */ }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.torna_indietro),
                        contentDescription = "Undo",
                        modifier = Modifier.size(24.dp)
                    )
                    val str = stringResource(R.string.cancel)
                    Text(text = str)
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        }
    }
}


fun getClue(g: Game, context: Context) {
    if(g.iSelect != null && g.jSelect != null){
        g.sudoku[g.iSelect!!][g.jSelect!!].value?.value = g.sudoku[g.iSelect!!][g.jSelect!!].sol
        g.sudoku[g.iSelect!!][g.jSelect!!].click?.value = 0
        g.oneSelect = false
        g.iSelect = null
        g.jSelect = null
        g.counter.value += 1
    } else {
        val str = context.getString(R.string.select)
        context.makeShortToast(str)
    }
}

fun eraseField(g: Game){
    if(g.iSelect != null && g.jSelect != null)
    {
        g.sudoku[g.iSelect!!][g.jSelect!!].note?.value = 0
    }
}

fun undoField(g: Game, u: Action) {
    if( u.r != null && u.c != null)
    {
        g.sudoku[u.r][u.c].value?.value = 0
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
    val note = rememberSaveable { mutableStateOf(false) }

    GameActionBar(s.getGame(), context, Action(note, 0, 0))
}