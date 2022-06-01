package com.example.sudoku.computation

import androidx.compose.runtime.Composable
import com.example.sudoku.model.Cell
import com.example.sudoku.model.Game
import kotlin.math.floor
import kotlin.math.sqrt

class Sudoku internal constructor(
    private var N: Int,/** number of columns/rows.**/
    private var K: Int /** No. Of missing digits**/
) {
    private var solution: Array<IntArray>
    private var mat: Array<Array<Cell>>
    private var srn: Int // square root of N

    // Sudoku Generator
    @Composable
    fun FillValues() {
        // Fill the diagonal of SRN x SRN matrices
        FillDiagonal()

        // Fill remaining blocks
        fillRemaining(0, srn)

        // Remove Randomly K digits to make Game
        removeKDigits()
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    @Composable
    private fun FillDiagonal() {
        var i = 0
        while (i < N) {
            // for diagonal box, start coordinates->i==j
            FillBox(i, i)
            i += srn
        }
    }

    // Returns false if given 3 x 3 block contains num.
    private fun unUsedInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until srn) for (j in 0 until srn) if (solution[rowStart + i][colStart + j] == num) return false
        return true
    }

    // Fill a 3 x 3 matrix.
    @Composable
    private fun FillBox(row: Int, col: Int) {
        var num: Int
        for (i in 0 until srn) {
            for (j in 0 until srn) {
                do {
                    num = randomGenerator(N)
                } while (!unUsedInBox(row, col, num))
                solution[row + i][col + j] = num
                //val val1 = rememberSaveable { mutableStateOf(num) }
                mat[row + i][col + j] = Cell(num, i, j, num)
            }
        }
    }

    // Random generator
    private fun randomGenerator(num: Int): Int {
        return floor(Math.random() * num + 1).toInt()
    }

    // Check if safe to put in cell
    private fun checkIfSafe(i: Int, j: Int, num: Int): Boolean {
        return unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % srn, j - j % srn, num)
    }

    // check in the row for existence
    private fun unUsedInRow(i: Int, num: Int): Boolean {
        for (j in 0 until N) if (solution[i][j] == num) return false
        return true
    }

    // check in the row for existence
    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0 until N) if (solution[i][j] == num) return false
        return true
    }

    // A recursive function to fill remaining
    // matrix
    @Composable
    private fun fillRemaining(n: Int, m: Int): Boolean {
        // System.out.println(i+" "+j);
        var i = n
        var j = m
        if (j >= N && i < N - 1) {
            i += 1
            j = 0
        }
        if (i >= N && j >= N) return true
        if (i < srn) {
            if (j < srn) j = srn
        } else if (i < N - srn) {
            if (j == (i / srn) * srn) j += srn
        } else {
            if (j == N - srn) {
                i += 1
                j = 0
                if (i >= N) return true
            }
        }
        for (num in 1..N) {
            if (checkIfSafe(i, j, num)) {
                solution[i][j] = num
                //val val1 = rememberSaveable { mutableStateOf(num) }
                mat[i][j] = Cell(num, i, j, num)
                if (fillRemaining(i, j + 1)) return true
                solution[i][j] = 0
                //val val2 = rememberSaveable { mutableStateOf(0) }
                mat[i][j] = Cell(0, i, j, 0)
            }
        }
        return false
    }

    // Remove the K no. of digits to
    // complete Game
    private fun removeKDigits() {
        var count = K
        while (count != 0) {
            val cellId = randomGenerator(N * N) - 1

            // System.out.println(cellId);
            // extract coordinates i and j
            val i = cellId / N
            var j = cellId % 9
            if (j != 0) j -= 1

            // System.out.println(i+" "+j);
            if (mat[i][j].value != 0) {
                count--
                mat[i][j].value = 0
            }
        }
    }

    // Print sudoku
    fun printSudoku() {
        for (i in 0 until N) {
            for (j in 0 until N) print(solution[i][j].toString() + " ")
            println()
        }
        println()
    }

    // Get Sudoku
    @Composable
    fun getGame(): Game {
        FillValues()
        val g = Game("", getSudoku(), getSolution())
        return g
    }

    // Get Sudoku
    private fun getSudoku(): Array<Array<Cell>> {
        return mat
    }

    // Get solution
    private fun getSolution(): Array<IntArray> {
        return solution
    }

    // Constructor
    init {
        // Compute square root of N
        val srn = sqrt(N.toDouble())
        this.srn = srn.toInt()
        solution = Array(N) { IntArray(N) }
        mat = Array(N) { Array(N){Cell(0,0,0,0)} }
    }
}
