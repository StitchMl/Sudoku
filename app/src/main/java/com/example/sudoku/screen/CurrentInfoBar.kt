package com.example.sudoku.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.computation.toTime
import com.example.sudoku.model.Game

/** Insert difficulty, mistakes and timer on the top of the grid*/
@Composable
fun CurrentInfoBar(g: Game, timer: MutableState<Long>) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
        Row(Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = g.difficult,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.mistakes) + "${g.mistakes.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            Text(text = timer.value.toTime(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun CurrentInfoBarPreview(){
    val context = LocalContext.current
    val set = Setting(context)
    val t = rememberSaveable { mutableLongStateOf(0L) }
    val k = rememberSaveable { mutableIntStateOf(0) }
    val diff = rememberSaveable { mutableStateOf(set.difficulty[1]) }
    set.setDifficult(diff.value, k)
    val s = Sudoku(9, k, diff)
    CurrentInfoBar(s.getGame(), t)
}