package com.example.sudoku.computation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.sudoku.model.Cell
import com.example.sudoku.model.Game
import com.example.sudoku.model.NumberBar
import kotlin.math.floor
import kotlin.math.sqrt

class Sudoku internal constructor(
    private var N: Int,/** number of columns/rows.**/
    private val K: MutableState<Int>, /** No. Of missing digits**/
    private val diff: MutableState<String>, /** Difficulty **/
) {
    private var solution: Array<IntArray>
    private var mat: Array<Array<Cell>>
    private var srn: Int
    private var bool = true

    /** Sudoku Generator **/
    @Composable
    fun FillValues() {
        if (bool) {
            // Fill the diagonal of SRN x SRN matrices
            FillDiagonal()

            // Fill remaining blocks
            fillRemaining(0, srn)

            // Remove Randomly K digits to make Game
            removeKDigits()
        }
    }

    /** Fill the diagonal SRN number of SRN x SRN matrices **/
    @Composable
    private fun FillDiagonal() {
        var i = 0
        while (i < N) {
            // for diagonal box, start coordinates->i==j
            FillBox(i, i)
            i += srn
        }
    }

    /** Returns false if given 3 x 3 block contains num. **/
    private fun unUsedInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until srn) for (j in 0 until srn) if (solution[rowStart + i][colStart + j] == num) return false
        return true
    }

    /** Fill a 3 x 3 matrix. **/
    @Composable
    private fun FillBox(row: Int, col: Int) {
        var num: Int
        for (i in 0 until srn) {
            for (j in 0 until srn) {
                do {
                    num = randomGenerator(N)
                } while (!unUsedInBox(row, col, num))
                solution[row + i][col + j] = num
                val value = rememberSaveable { mutableStateOf(num) }
                mat[row + i][col + j].row = i
                mat[row + i][col + j].col = j
                mat[row + i][col + j].sol = num
                mat[row + i][col + j].value = value
            }
        }
    }

    /** Random generator **/
    private fun randomGenerator(num: Int): Int {
        return floor(Math.random() * num + 1).toInt()
    }

    /** Check if safe to put in cell **/
    private fun checkIfSafe(i: Int, j: Int, num: Int): Boolean {
        return unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % srn, j - j % srn, num)
    }

    /** check in the row for existence **/
    private fun unUsedInRow(i: Int, num: Int): Boolean {
        for (j in 0 until N) if (solution[i][j] == num) return false
        return true
    }

    /** check in the row for existence **/
    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0 until N) if (solution[i][j] == num) return false
        return true
    }

    /** A recursive function to fill remaining **/
    @Composable
    private fun fillRemaining(n: Int, m: Int): Boolean {
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
                mat[i][j].row = i
                mat[i][j].col = j
                val value = rememberSaveable { mutableStateOf(num) }
                solution[i][j] = num
                mat[i][j].sol = num
                mat[i][j].value = value
                if (fillRemaining(i, j + 1)) return true
                val value1 = rememberSaveable { mutableStateOf(0) }
                solution[i][j] = 0
                mat[i][j].sol = 0
                mat[i][j].value = value1
            }
        }
        return false
    }

    // complete Game
    /** Remove the K no. of digits to **/
    private fun removeKDigits() {
        var count = K.value
        while (count != 0) {
            val cellId = randomGenerator(N * N) - 1

            // extract coordinates i and j
            val i = cellId / N
            var j = cellId % 9
            if (j != 0) j -= 1

            if (mat[i][j].value?.value != 0) {
                count--
                mat[i][j].value?.value = 0
            }
        }
    }

    /** Print solution **/
    fun printSolution() {
        for (i in 0 until N) {
            for (j in 0 until N) print(solution[i][j].toString() + " ")
            println()
        }
        println()
    }

    /** Print sudoku **/
    fun printSudoku() {
        for (i in 0 until N) {
            for (j in 0 until N) print(mat[i][j].value.toString() + " ")
            println()
        }
        println()
    }

    /** Get Game Set **/
    @Composable
    fun getGame(): Game {
        FillValues()
        bool = false
        val counter = rememberSaveable { mutableStateOf((N*N)-K.value) }
        return Game(diff, getSudoku(), NumberBar(), counter = counter)
    }

    /** Get Sudoku **/
    private fun getSudoku(): Array<Array<Cell>> {
        return mat
    }

    /** Get solution **/
    private fun getSolution(): Array<IntArray> {
        return solution
    }

    /** Activate change game **/
    fun changeGame(){
        bool = true
        solution = Array(N) { IntArray(N) }
        mat = Array(N) { Array(N){Cell(0,0,0, null, null)} }
    }

    /** Constructor **/
    init {
        // Compute square root of N
        val srn = sqrt(N.toDouble())
        this.srn = srn.toInt()
        solution = Array(N) { IntArray(N) }
        mat = Array(N) { Array(N){Cell(0,0,0, null, null)} }
    }
}
