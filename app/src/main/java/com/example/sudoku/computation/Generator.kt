package com.example.sudoku.computation

import com.example.sudoku.`object`.Grid
import kotlin.random.Random

/**
*Random Grid Generator*
returns randomly filled grids with specified number of blank cells
the number of empty cells increases as the difficulty increases
 **/
private fun generate(numberOfEmptyCells: Int): Grid {
    return generate(5) //messo a caso, va creata funzione per generare automaticamente
}

fun generateFullSolution(): Grid {
    return generate(4) //param. messo a caso, va creata funzione per generare automaticamente
}

fun eraseCells(grid: Grid, numberOfEmptyCells: Int) {
    var i = 0
    while (i < numberOfEmptyCells) {
        val randomRow = Random.nextInt(9)
        val randomColumn = Random.nextInt(9)
        val cell = grid.getCell(randomRow, randomColumn)
        if (!cell!!.isEmpty) {
            cell.value = 0
        } else {
            i--
        }
        i++
    }
}