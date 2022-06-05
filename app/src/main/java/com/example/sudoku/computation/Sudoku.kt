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
    private var n: Int,/** number of columns/rows.**/
    private val k: MutableState<Int>,/** No. Of missing digits**/
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
        while (i < n) {
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
                    num = randomGenerator(n)
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
        for (j in 0 until n) if (solution[i][j] == num) return false
        return true
    }

    /** check in the row for existence **/
    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0 until n) if (solution[i][j] == num) return false
        return true
    }

    /** A recursive function to fill remaining **/
    @Composable
    private fun fillRemaining(n: Int, m: Int): Boolean {
        val i = rememberSaveable { mutableStateOf(n) }
        val j = rememberSaveable { mutableStateOf(m) }
        val bool = rememberSaveable { mutableStateOf(false) }
        if (j.value >= this.n && i.value < this.n - 1) {
            i.value += 1
            j.value = 0
        }
        if (i.value >= this.n && j.value >= this.n) bool.value = true
        CheckNumInBox(i, j, bool)
        SetNumInSudo(i, j, bool)
        bool.value = false
        return bool.value
    }

    @Composable
    private fun CheckNumInBox(i: MutableState<Int>, j: MutableState<Int>, bool: MutableState<Boolean>){
        if (i.value < srn) {
            if (j.value < srn) j.value = srn
        } else if (i.value < this.n - srn) {
            if (j.value == (i.value / srn) * srn) j.value += srn
        } else {
            if (j.value == this.n - srn) {
                i.value += 1
                j.value = 0
                if (i.value >= this.n) bool.value = true
            }
        }
    }

    @Composable
    private fun SetNumInSudo(i: MutableState<Int>, j: MutableState<Int>, bool: MutableState<Boolean>){
        for (num in 1..this.n) {
            if (checkIfSafe(i.value, j.value, num)) {
                mat[i.value][j.value].row = i.value
                mat[i.value][j.value].col = j.value
                val value = rememberSaveable { mutableStateOf(num) }
                solution[i.value][j.value] = num
                mat[i.value][j.value].sol = num
                mat[i.value][j.value].value = value
                if (fillRemaining(i.value, j.value + 1)) bool.value = true
                val value1 = rememberSaveable { mutableStateOf(0) }
                solution[i.value][j.value] = 0
                mat[i.value][j.value].sol = 0
                mat[i.value][j.value].value = value1
            }
        }
    }

    // complete Game
    /** Remove the K no. of digits to **/
    private fun removeKDigits() {
        var count = k.value
        while (count != 0) {
            val cellId = randomGenerator(n * n) - 1

            // extract coordinates i and j
            val i = cellId / n
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
        for (i in 0 until n) {
            for (j in 0 until n) print(solution[i][j].toString() + " ")
            println()
        }
        println()
    }

    /** Print sudoku **/
    fun printSudoku() {
        for (i in 0 until n) {
            for (j in 0 until n) print(mat[i][j].value.toString() + " ")
            println()
        }
        println()
    }

    /** Get Game Set **/
    @Composable
    fun getGame(): Game {
        FillValues()
        bool = false
        val counter = rememberSaveable { mutableStateOf((n*n)-k.value) }
        return Game(diff, getSudoku(), NumberBar(), counter = counter)
    }

    /** Get Sudoku **/
    private fun getSudoku(): Array<Array<Cell>> {
        return mat
    }

    /** Activate change game **/
    fun changeGame(){
        bool = true
        solution = Array(n) { IntArray(n) }
        mat = Array(n) { Array(n){Cell(0,0,0, null, null)} }
    }

    /** Constructor **/
    init {
        // Compute square root of N
        val srn = sqrt(n.toDouble())
        this.srn = srn.toInt()
        solution = Array(n) { IntArray(n) }
        mat = Array(n) { Array(n){Cell(0,0,0, null, null)} }
    }
}
