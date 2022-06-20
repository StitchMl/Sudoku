package com.example.sudoku.computation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.sudoku.model.*
import java.security.SecureRandom
import kotlin.math.floor
import kotlin.math.sqrt

class Sudoku internal constructor(
    private var n: Int,
    /** number of columns/rows.**/
    private val k: MutableState<Int>,
    /** No. Of missing digits**/
    private val diff: MutableState<String>
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
                val mutableNum = rememberSaveable { mutableStateOf(0) }
                mat[row + i][col + j].row = i
                mat[row + i][col + j].col = j
                mat[row + i][col + j].sol = num
                mat[row + i][col + j].value = value
                mat[row + i][col + j].note = mutableNum
            }
        }
    }

    /** Random generator **/
    private fun randomGenerator(num: Int): Int {
        val random = SecureRandom() // Compliant
        return floor(random.nextDouble() * num + 1).toInt()
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
        if (j.value >= this.n && i.value < this.n - 1) {
            i.value += 1
            j.value = 0
        }
        if (i.value >= this.n && j.value >= this.n) return true
        if(checkNumInBox(i, j)) return true
        if(setNumInSudo(i, j)) return true
        return false
    }

    @Composable
    private fun checkNumInBox(i: MutableState<Int>, j: MutableState<Int>): Boolean{
        if (i.value < srn) {
            if (j.value < srn) j.value = srn
        } else if (i.value < this.n - srn) {
            if (j.value == (i.value / srn) * srn) j.value += srn
        } else {
            if (j.value == this.n - srn) {
                i.value += 1
                j.value = 0
                if (i.value >= this.n) return true
            }
        }
        return false
    }

    @Composable
    private fun setNumInSudo(i: MutableState<Int>, j: MutableState<Int>): Boolean{
        for (num in 1..this.n) {
            if (checkIfSafe(i.value, j.value, num)) {
                mat[i.value][j.value].row = i.value
                mat[i.value][j.value].col = j.value
                val value = rememberSaveable { mutableStateOf(num) }
                val mutableNum = rememberSaveable { mutableStateOf(0) }
                solution[i.value][j.value] = num
                mat[i.value][j.value].sol = num
                mat[i.value][j.value].value = value
                mat[i.value][j.value].note = mutableNum
                if (fillRemaining(i.value, j.value + 1)) return true
                val value1 = rememberSaveable { mutableStateOf(0) }
                solution[i.value][j.value] = 0
                mat[i.value][j.value].sol = 0
                mat[i.value][j.value].value = value1
            }
        }
        return false
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
        val counter = rememberSaveable { mutableStateOf((n * n) - k.value) }
        val mistakes = rememberSaveable { mutableStateOf(0) }
        val note = rememberSaveable { mutableStateOf(false) }
        return Game(diff.value, sudoku = getSudoku(), bar = NumberBar(), counter = counter, solution = getSolution(), mistakes = mistakes, note = Action(0, 0, note))
    }

    /** Get Sudoku **/
    private fun getSudoku(): Array<Array<Cell>> {
        return mat
    }

    /** Get Sudoku **/
    private fun getSolution(): Array<IntArray> {
        return solution
    }

    /** Activate change game **/
    fun changeGame(){
        bool = true
        solution = Array(n) { IntArray(n) }
        mat = Array(n) { Array(n){Cell(0,0,0, null, null, null) }}
    }

    /** Save Game **/
    fun saveGame(sudoku: Array<Array<Cell>>): Array<IntArray>{
        val savedSudoku = Array(9){IntArray(9)}
        for (i in sudoku.indices){
            for (j in 0 until sudoku[i].size){
                savedSudoku[i][j] = sudoku[i][j].value?.value!!
            }
        }
        return savedSudoku
    }

    @Composable
    fun setGame(sudoku: Score): Game {
        bool = false
        val counter = rememberSaveable { mutableStateOf(sudoku.counter) }
        val sol = Array(9){IntArray(9)}
        val cellList = sudoku.sudoku.split(',')
        val cellSolList = sudoku.solution.split(',')
        for (i in 0 until cellList.size-1){
            val sCell = cellList[i].substring(1, cellList[i].length - 1)
            val sSolCell = cellSolList[i].substring(1, cellSolList[i].length - 1)
            val lC = sCell.split(';')
            val lSC = sSolCell.split(';')
            val value = rememberSaveable { mutableStateOf(lC[2].toInt()) }
            val num = rememberSaveable { mutableStateOf(0) }
            mat[lC[0].toInt()][lC[1].toInt()] = Cell(lC[0].toInt(), lC[1].toInt(), lSC[2].toInt(), value, null, num)
            sol[lSC[0].toInt()][lSC[1].toInt()] = lSC[2].toInt()
        }
        solution = sol
        diff.value = sudoku.diff
        val mistakes = rememberSaveable { mutableStateOf(sudoku.mistakes) }
        val note = rememberSaveable { mutableStateOf(false) }
        return Game(diff.value, mistakes = mistakes, sudoku = getSudoku(), bar = NumberBar(), counter = counter, solution = getSolution(), note = Action(0, 0, note))
    }

    /** Constructor **/
    init {
        // Compute square root of N
        val srn = sqrt(n.toDouble())
        this.srn = srn.toInt()
        solution = Array(n) { IntArray(n) }
        mat = Array(n) { Array(n){Cell(0,0,0, null, null, null)} }
    }
}
