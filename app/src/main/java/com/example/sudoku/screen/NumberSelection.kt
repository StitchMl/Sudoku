package com.example.sudoku.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.computation.makeShortToast
import com.example.sudoku.model.Game


/** Create the board of number to insert **/
@Composable
fun NumberSelection(g: Game, context: Context) {
    val tempVal = rememberSaveable { mutableStateOf(false) }
    val str = stringResource(R.string.wrong)
    val s = context.getString(R.string.select)
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

                val textColor = if (g.numb.value[it - 1] == 9) {
                    MaterialTheme.colors.background // Colore di sfondo se il valore è 9
                } else {
                    MaterialTheme.colors.onBackground // Colore normale
                }

                Box(
                    modifier = Modifier
                        .size(itemSize)
                        .background(if (clicked.value) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.background)
                        .clickable {
                            // Gestisce il click su un numero nella barra
                            clickAction(str, s, it, g, context)
                        } ,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.toString(),
                        color = textColor,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                setNote(g)
            }
        }
    }
}

fun setNote(g: Game) {
    if(g.iSelect!= null) {
        g.note.r = g.iSelect!!
    }
    if(g.jSelect!= null) {
        g.note.c = g.jSelect!!
    }
}


fun clickAction(str: String, s: String, it: Int, g: Game, context: Context){
    // Se la modalità note è attiva
    if (g.note.note.value) {
        noteAction(g, s, it, context)
    } else {
        // Altrimenti si inserisce il numero
        insertNum(g, str, it, context)
        if (g.mistakes.value == 3) {
            g.counter.value = 0
        }
    }
}

fun insertNum(g: Game, str: String, it: Int, context: Context) {
    if (g.iSelect != null && g.jSelect != null) {
        // Inserisce il numero nella cella selezionata
        oneIsSelect(str, it, g, context)
    } else {
        // Se nessuna cella è selezionata, seleziona il numero dalla barra
        selectNumOnBar(it, g)
        for (i in 0 until g.sudoku.size) {
            for (j in 0 until g.sudoku[i].size) {
                // Mostra solo le celle che hanno il valore selezionato
                if (g.sudoku[i][j].value?.value == it) {
                    g.sudoku[i][j].click?.value = 2 // Evidenzia le celle corrispondenti
                } else {
                    g.sudoku[i][j].click?.value = 0
                }
            }
        }
    }
}

fun noteAction(g: Game, s: String, it: Int, context: Context) {
    if (g.iSelect != null && g.jSelect != null) {
        val cell = g.sudoku[g.iSelect!!][g.jSelect!!]

        // Aggiungi o rimuovi la nota e aggiorna immediatamente lo stato
        if(cell.note?.intValue == it){
            cell.note?.intValue = it+10
        } else {
            cell.note?.intValue = it
        }
        Log.d("noteAction", "cell.note = ${cell.note?.intValue}")
    } else {
        context.makeShortToast(s)
    }
}

fun oneIsSelect(str: String, it: Int, g: Game, context: Context) {
    if (g.sudoku[g.iSelect!!][g.jSelect!!].sol == it) {
        g.sudoku[g.iSelect!!][g.jSelect!!].value?.value = it
        g.sudoku[g.iSelect!!][g.jSelect!!].click?.value = 0 // Deseleziona la cella
        g.oneSelect = false
        g.iSelect = null
        g.jSelect = null
        g.counter.value += 1
        g.numb.value[it - 1] += 1
    } else {
        g.mistakes.value++
        context.makeShortToast(str)
    }
}

fun selectNumOnBar(it: Int, g: Game){
    // Aggiorna lo stato del numero selezionato nella barra
    if (g.bar.select != 0) {
        g.bar.bar[g.bar.select-1]?.value = false
        g.bar.bar[it - 1]?.value = true
        g.bar.select = it
    } else {
        g.bar.bar[it - 1]?.value = true
        g.bar.select = it
    }
}


@Preview(showBackground = true)
@Composable
fun ShowPreview() {
    val d = rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current
    val set = Setting(context)
    val diff = rememberSaveable { mutableStateOf(set.difficulty[0]) }
    set.setDifficult(diff.value, d)
    val s = Sudoku(9, d, diff)
    val game = s.getGame()
    NumberSelection(game, context)
}