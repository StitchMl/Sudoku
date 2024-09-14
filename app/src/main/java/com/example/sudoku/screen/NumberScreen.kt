package com.example.sudoku.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.model.Game

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun ShowNUmberSelection(){
    val d = rememberSaveable { mutableIntStateOf(0) }
    val context = LocalContext.current
    val set = Setting(context)
    val diff = rememberSaveable { mutableStateOf(set.difficulty[0]) }
    set.setDifficult(diff.value, d)
    val s = Sudoku(9, d, diff)
    val game = s.getGame()
    Column {
        Text(
            "${diff.value} ${d.intValue}",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        )
        CreateBoard(game)
    }
}

@Composable
fun CreateBoard(
    game: Game
) {
    BoxWithConstraints {
        val itemSize = (maxWidth-8.dp) / 9

        Column(modifier = Modifier.padding(4.dp)) {
            (0..2).forEach { n ->
                Row(modifier = Modifier.border(1.dp, Color.Black)
                ) { (0..2).forEach { m ->
                        Column(modifier = Modifier
                                .border(1.dp, Color.Black)
                        ) { (0..2).forEach { i ->
                                Row { (0..2).forEach { j ->
                                    val row = i + (n * 3)
                                    val col = j + (m * 3)
                                    CreateCell(row, col, game, itemSize) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun cellSelect(i: Int, j: Int, g: Game){
    // Caso in cui la cella è già selezionata: deseleziona
    if(g.sudoku[i][j].click?.value == 1){
        g.jSelect = null
        g.iSelect = null
        g.oneSelect = false
        g.sudoku[i][j].click?.value = 0
    }
    // Caso in cui un'altra cella è già selezionata: cambia selezione
    else if (g.oneSelect){
        g.sudoku[g.iSelect!!][g.jSelect!!].click?.value = 0 // Deseleziona la cella precedente
        g.iSelect = i // Aggiorna la selezione
        g.jSelect = j
        g.sudoku[i][j].click?.value = 1 // Seleziona la nuova cella
    }
    // Nessuna cella selezionata: seleziona la nuova
    else {
        g.oneSelect = true
        g.iSelect = i
        g.jSelect = j
        // Deseleziona tutte le altre celle
        for (row in 0 until g.sudoku.size){
            for (col in 0 until g.sudoku[row].size){
                    g.sudoku[row][col].click?.value = 0
            }
        }
        // Seleziona la nuova cella
        g.sudoku[i][j].click?.value = 1
        // Resetta la selezione della barra numerica, se necessario
        if (g.bar.select != 0) {
            g.bar.bar[g.bar.select-1]?.value = false
            g.bar.select = 0
        }
    }
}

@Composable
fun CreateCell(row: Int, col: Int, game: Game, itemSize: Dp) {
    val cell = game.sudoku[row][col]
    val click = rememberSaveable { mutableIntStateOf(0) }

    // Rimuoviamo la gestione manuale delle noteMatrix e lo sostituiamo con uno stato osservabile
    val notesMatrix = remember { mutableStateListOf(0, 0, 0, 0, 0, 0, 0, 0, 0) }

    // Aggiorna la matrice delle note quando cambiano
    LaunchedEffect(cell.note?.intValue) {
        Log.d("CreateCell", "note = ${cell.note?.intValue}")
        val v = cell.note?.intValue
        if (v != null) {
            if(v>0) {
                if (v>=10) {
                    notesMatrix[v-10 - 1] = 0  // Imposta il valore corrispondente a 0
                } else if (notesMatrix[v - 1] == v){
                    notesMatrix[v - 1] = 0  // Imposta il valore corrispondente a 0
                } else {
                    notesMatrix[v - 1] = v // Imposta il valore corrispondente alla nota
                }
            }
        }
    }

    // Settiamo il click attuale della cella
    cell.click = click

    // Ritorno della Box principale per visualizzare la cella
    Box(
        modifier = Modifier
            .border(1.dp, Color.LightGray)
            .background(
                // Gestione del colore di sfondo in base alla selezione
                when (click.intValue) {
                    1 -> Color.Gray // Cella selezionata
                    2 -> Color.LightGray
                    else -> Color.White // Cella non selezionata
                }
            )
            .size(itemSize)
            .clickable {
                // Se la cella è vuota, la selezione può essere modificata
                if (cell.value?.value == 0) {
                    cellSelect(row, col, game) // Gestione della selezione della cella
                }
            },
        contentAlignment = Alignment.Center
    ) {
        // Se la cella ha un valore, mostra quel valore
        if (cell.value?.value != 0) {
            // Mostra il valore principale
            Text(
                text = cell.value?.value.toString(),
                style = TextStyle(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
            )
        } else {
            // Mostra le note se la cella è vuota
            cell.note?.let {
                NotesGrid(notesMatrix) // Chiamiamo NotesGrid per mostrare la griglia delle note
            }
        }
    }
}

@Composable
fun NotesGrid(notesMatrix: List<Int>) {
    // Aggiorniamo la visualizzazione della griglia delle note
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        (0..2).forEach { i ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                (0..2).forEach { j ->
                    val noteValue = notesMatrix[i * 3 + j]
                    Text(
                        text = if(noteValue != 0){
                            noteValue.toString()
                        }else{
                            ""
                        },
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .padding(1.dp)
                    )
                }
            }
        }
    }
}




