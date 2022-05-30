package com.example.sudoku.`class`

import kotlin.random.Random

//genera griglie random
class Generator {
    //ritorna griglie riempite a caso con il numero specificato di celle vuote
    //il numero di celle vuote aumenta all'aumentare della difficoltà
    //
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
}