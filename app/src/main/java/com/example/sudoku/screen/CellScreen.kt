package com.example.sudoku.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
private fun ShowNUmberSelection(){
    SudokuCell(Cell(3,3,9)){ /*TODO*/ }
}

@Composable
fun SudokuCell(
    cell: Cell,
    //selectionState: CellSelectionState,
    onClick: () -> Unit
) {
    val (row, col) = cell

    BoxWithConstraints(modifier = Modifier
        //.background(color = color) //da mettere il colore che cambia al click
        .fillMaxSize()
        .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (cell.selection != null) {
            Text(
                text = cell.selection.toString(),
                //fontSize = //vediamo quel mettere
            )
        } else if (cell.notes.isNotEmpty()) {
            CellNotesGrid(containerSize = maxWidth, notes = cell.notes)
        }
    }
}

@Composable
private fun CellNotesGrid(containerSize: Dp, notes: Set<Int>) {
    val size = containerSize / 3

    Row {
        repeat(3) { row ->
            Column {
                repeat(3) { col ->
                    val number = row * 3 + col + 1

                    Text(
                        text = if (number in notes) number.toString() else "",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.size(size),
                        fontSize = 10.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

typealias CellCoordinates = Pair<Int, Int>

typealias Board = List<List<Cell>>

fun Board.getCell(index: CellCoordinates): Cell {
    val (row, col) = index

    return this[row][col]
}

fun Board.copyWithValue(coordinates: CellCoordinates, value: Int?): Board =
    updateCell(coordinates) { copy(selection = value) }

fun Board.toggleNote(coordinates: CellCoordinates, value: Int): Board =
    updateCell(coordinates) {
        val noteCopy = this.notes.toMutableSet()

        if (value in noteCopy) {
            noteCopy -= value
        } else {
            noteCopy += value
        }

        copy(notes = noteCopy)
    }

fun Board.updateCell(
    coordinates: CellCoordinates,
    updater: Cell.() -> Cell
): Board {
    return this.toMutableList().mapIndexed { index, list ->
        val inner = list.toMutableList()

        if (coordinates.first == index) {
            inner[coordinates.second] = updater(inner[coordinates.second])
        }

        inner
    }
}