package com.example.sudoku.`object`

import kotlin.random.Random

/**
 * Solver risolve il Sudoku.
 */
class Solver {
    private val values: IntArray

    /**
     * @param griglia da risolvere
     * @throws IllegalStateException in caso la griglia non sia valida
     */
    fun solve(grid: Grid) {
        val solvable = solve(grid, grid.firstEmptyCell)
        check(solvable) { "The provided grid is not solvable." }
    }

    private fun solve(grid: Grid, cell: Grid.Cell?): Boolean {
        if (cell == null) {
            return true
        }
        for (value in values) {
            if (grid.isValidValueForCell(cell, value)) {
                cell.value = value
                if (solve(grid, grid.getNextEmptyCellOf(cell))) return true
                cell.value = EMPTY
            }
        }
        return false
    }

    private fun generateRandomValues(): IntArray {
        val values = intArrayOf(EMPTY, 1, 2, 3, 4, 5, 6, 7, 8, 9)
        var i = 0
        var j = Random.nextInt(9)
        var tmp = values[j]
        while (i < values.size) {
            if (i == j) {
                i++
                j = Random.nextInt(9)
                tmp = values[j]
                continue
            }
            values[j] = values[i]
            values[i] = tmp
            i++
            j = Random.nextInt(9)
            tmp = values[j]
        }
        return values
    }

    companion object {
        private const val EMPTY = 0
    }

    /**
     * costruisce un nuovo caso di Solver.
     */
    init {
        values = generateRandomValues()
    }
}