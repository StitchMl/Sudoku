package com.example.sudoku.computation

import kotlin.math.floor
import kotlin.math.sqrt

class Sudoku internal constructor(
    private var N: Int,/** number of columns/rows.**/
    private var K: Int /** No. Of missing digits**/
) {
    private var mat: Array<IntArray>
    private lateinit var solution: Array<IntArray>
    private var srn: Int // square root of N

    // Sudoku Generator
    fun fillValues() {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal()

        // Fill remaining blocks
        fillRemaining(0, srn)

        //Set solution
        solution = mat

        // Remove Randomly K digits to make Game
        removeKDigits()
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    private fun fillDiagonal() {
        var i = 0
        while (i < N) {
            // for diagonal box, start coordinates->i==j
            fillBox(i, i)
            i += srn
        }
    }

    // Returns false if given 3 x 3 block contains num.
    private fun unUsedInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0 until srn) for (j in 0 until srn) if (mat[rowStart + i][colStart + j] == num) return false
        return true
    }

    // Fill a 3 x 3 matrix.
    private fun fillBox(row: Int, col: Int) {
        var num: Int
        for (i in 0 until srn) {
            for (j in 0 until srn) {
                do {
                    num = randomGenerator(N)
                } while (!unUsedInBox(row, col, num))
                mat[row + i][col + j] = num
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
        for (j in 0 until N) if (mat[i][j] == num) return false
        return true
    }

    // check in the row for existence
    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0 until N) if (mat[i][j] == num) return false
        return true
    }

    // A recursive function to fill remaining
    // matrix
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
                mat[i][j] = num
                if (fillRemaining(i, j + 1)) return true
                mat[i][j] = 0
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
            if (mat[i][j] != 0) {
                count--
                mat[i][j] = 0
            }
        }
    }

    // Print sudoku
    fun printSudoku() {
        for (i in 0 until N) {
            for (j in 0 until N) print(mat[i][j].toString() + " ")
            println()
        }
        println()
    }

    // Get Sudoku
    fun get(): Array<IntArray> {
        return mat
    }

    // Get solution
    fun getSolution(): Array<IntArray> {
        return solution
    }

    companion object {
        // Driver code
        @JvmStatic
        fun main(args: Array<String>) {
            val n = 9
            val k = 20
            val sudoku = Sudoku(n, k)
            sudoku.fillValues()
            sudoku.printSudoku()
        }
    }

    // Constructor
    init {
        // Compute square root of N
        val srn = sqrt(N.toDouble())
        this.srn = srn.toInt()
        mat = Array(N) { IntArray(N) }
    }
}
