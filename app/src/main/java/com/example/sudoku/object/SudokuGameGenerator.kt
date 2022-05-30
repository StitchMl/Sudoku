package com.example.sudoku.`object`

import com.example.sudoku.computation.eraseCells
import com.example.sudoku.computation.generateFullSolution
import com.example.sudoku.model.Cell

fun generateBoard(emptyCells: Int = 42): List<List<Cell>> {
    val grid = generateFullSolution()

    val solution = List(9) { row ->
        List(9) { col ->
            grid.getCell(row, col)!!.value
        }
    }

    eraseCells(grid, emptyCells)

    return List(9) { row ->
        List(9) { col ->
            val value = grid.getCell(row, col)!!.value.let { if (it == 0) null else it }
            Cell(row, col,
                selection = value,
                preset = value != null,
                correctValue = solution[row][col]
            )
        }
    }
}