package com.example.sudoku.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.computation.makeShortToast
import com.example.sudoku.model.Game


@Composable
fun GameActionBar(game: Game, context: Context) {
    val clue = rememberSaveable { mutableIntStateOf(3) }
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 85.dp),
            horizontalArrangement = Arrangement.SpaceBetween
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

            IconButton(onClick = { game.note.note.value = !game.note.note.value },
            modifier = Modifier.background(if (game.note.note.value) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.background)) {
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
            IconButton(onClick = { getClue(game, clue, context) }) {
                Box {
                    // Icon and text as before
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.suggerimenti),
                            contentDescription = "Clue",
                            modifier = Modifier.size(24.dp)
                        )
                        val str = stringResource(R.string.clue)
                        Text(text = str)
                    }

                    // Red dot with clue value in the top right corner
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color.Red, shape = CircleShape)
                            .align(Alignment.TopEnd), // Align the red dot to the top right of the Icon
                            //.offset(x = 8.dp, y = (-8).dp), // Positioning the dot above and to the right of the icon
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.offset(x = 0.dp, y = (-2).dp),
                            text = "${clue.intValue}",
                            color = Color.White,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        }
    }
}


fun getClue(g: Game, clue: MutableIntState, context: Context) {
    if(g.iSelect != null && g.jSelect != null && clue.intValue!=0){
        g.sudoku[g.iSelect!!][g.jSelect!!].value?.value = g.sudoku[g.iSelect!!][g.jSelect!!].sol
        g.sudoku[g.iSelect!!][g.jSelect!!].click?.value = 0
        g.oneSelect = false
        g.iSelect = null
        g.jSelect = null
        g.counter.value += 1
        clue.intValue -= 1
    } else if (clue.intValue==0) {
        val str = context.getString(R.string.clue_count)
        context.makeShortToast(str)
    } else {
            val str = context.getString(R.string.select)
            context.makeShortToast(str)
    }
}

fun eraseField(g: Game){
    if(g.iSelect != null && g.jSelect != null)
    {
        g.sudoku[g.iSelect!!][g.jSelect!!].note?.intValue = -1
    }
}



@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun ButtonActionPreview(){
    val context = LocalContext.current
    val set = Setting(context)
    val k = rememberSaveable { mutableIntStateOf(0) }
    val diff = rememberSaveable { mutableStateOf(set.difficulty[1]) }
    set.setDifficult(diff.value, k)
    val s = Sudoku(9, k, diff)

    GameActionBar(s.getGame(), context)
}