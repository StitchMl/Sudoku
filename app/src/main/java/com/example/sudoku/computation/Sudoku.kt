package com.example.sudoku.computation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.sudoku.model.Action
import com.example.sudoku.model.Cell
import com.example.sudoku.model.Game
import com.example.sudoku.model.NumberBar
import com.example.sudoku.model.Score
import java.security.SecureRandom
import kotlin.math.floor
import kotlin.math.sqrt

class Sudoku internal constructor(
    private var n: Int,/** number of columns/rows.**/
    private val k: MutableState<Int>,/** No. Of missing digits**/
    private val diff: MutableState<String>
) {
    private var solution: Array<IntArray>
    private var mat: Array<Array<Cell>>
    private var numb = mutableStateOf(IntArray(n))
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

            //Set numb
            for(i in 0 until 9){
                numb.value[i]=9
            }

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
        for (i in 0 until srn) {
            for (j in 0 until srn) {
                if (solution[rowStart + i][colStart + j] == num) {
                    return false
                }
            }
        }
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
                val value = rememberSaveable { mutableIntStateOf(num) }
                val mutableNum = rememberSaveable { mutableIntStateOf(0) }
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
        val random = SecureRandom()
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
        for (j in 0 until n) {
            if (solution[i][j] == num) {
                return false
            }
        }
        return true
    }

    /** check in the row for existence **/
    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0 until n) {
            if (solution[i][j] == num) {
                return false
            }
        }
        return true
    }

    /** A recursive function to fill remaining **/
    @Composable
    private fun fillRemaining(n: Int, m: Int): Boolean {
        val i = rememberSaveable { mutableIntStateOf(n) }
        val j = rememberSaveable { mutableIntStateOf(m) }
        if (j.intValue >= this.n && i.intValue < this.n - 1) {
            i.intValue += 1
            j.intValue = 0
        }
        if (i.intValue >= this.n && j.intValue >= this.n) return true
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
                val value = rememberSaveable { mutableIntStateOf(num) }
                val mutableNum = rememberSaveable { mutableIntStateOf(0) }
                solution[i.value][j.value] = num
                mat[i.value][j.value].sol = num
                mat[i.value][j.value].value = value
                mat[i.value][j.value].note = mutableNum
                if (fillRemaining(i.value, j.value + 1)) return true
                val value1 = rememberSaveable { mutableIntStateOf(0) }
                solution[i.value][j.value] = 0
                mat[i.value][j.value].sol = 0
                mat[i.value][j.value].value = value1
            }
        }
        return false
    }

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
                numb.value[mat[i][j].value?.value!!-1] -= 1
                mat[i][j].value?.value = 0
            }
        }
    }

    /** Get Game Set **/
    @Composable
    fun getGame(): Game {
        FillValues()
        bool = false
        val counter = rememberSaveable { mutableIntStateOf((n * n) - k.value) }
        val mistakes = rememberSaveable { mutableIntStateOf(0) }
        val note = rememberSaveable { mutableStateOf(false) }
        return Game(diff.value, sudoku = getSudoku(), bar = NumberBar(), counter = counter, numb = getNumb(), solution = solutionArray, mistakes = mistakes, note = Action(0, 0, note))
    }

    /** Get Sudoku **/
    private fun getSudoku(): Array<Array<Cell>> {
        return mat
    }

    /** Get Solution **/
    private val solutionArray: Array<IntArray>
        get() = solution


    /** Activate change game **/
    fun changeGame(){
        bool = true
        solution = Array(n) { IntArray(n) }
        mat = Array(n) { Array(n){Cell(0,0,0, null, null, null) }}
    }

    /** Get Numb **/
    private fun getNumb(): MutableState<IntArray> {
        return numb
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

    /** Extract Game from Score **/
    @Composable
    fun setGame(sudoku: Score): Game {
        bool = false
        val counter = rememberSaveable { mutableIntStateOf(sudoku.counter) }

        // Array che rappresenta la soluzione di Sudoku.
        val sol = Array(9){IntArray(9)}

        // Parsing delle stringhe per ottenere gli array di celle e soluzioni.
        val cellList = sudoku.sudoku.split(',')
        val cellSolList = sudoku.solution.split(',')
        val cellNumbList = sudoku.numb.split(',')

        // Creiamo un MutableState per la lista dei numeri del Sudoku.
        val numbArray = IntArray(9)  // Creiamo un IntArray
        for (i in 0 until 9) {
            // Trasformiamo la lista in valori numerici.
            val sNumbCell = cellNumbList[i].substring(1, cellNumbList[i].length - 1)
            //Log.d("Sudoku", "numb = $sNumbCell")
            val lN = sNumbCell.split(';')  // lN[0] = x, lN[1] = y, lN[2] = value
            //Log.d("Sudoku", "numb = $lN")
            numbArray[lN[0].toInt()] = lN[1].toInt()  // Assegniamo i valori
        }

        // Creiamo un MutableState per il numero.
        val numbs = rememberSaveable { mutableStateOf(numbArray) }

        // Creiamo una matrice di celle (mat) che contiene tutte le celle del Sudoku
        for (i in 0 until cellList.size-1){
            // Otteniamo la cella e la sua soluzione dalla stringa.
            val sCell = cellList[i].substring(1, cellList[i].length - 1)
            val sSolCell = cellSolList[i].substring(1, cellSolList[i].length - 1)

            // Splittiamo la stringa in base a `;` per ottenere le coordinate e i valori.
            val lC = sCell.split(';')
            val lSC = sSolCell.split(';')

            // Creiamo dei MutableState per il valore della cella e il numero.
            val value = rememberSaveable { mutableIntStateOf(lC[2].toInt()) }
            val num = rememberSaveable { mutableIntStateOf(0) }

            // Assegniamo i valori alla matrice di celle.
            mat[lC[0].toInt()][lC[1].toInt()] = Cell(lC[0].toInt(), lC[1].toInt(), lSC[2].toInt(), value, null, num)

            // Assegniamo il valore della soluzione alla matrice `sol`.
            sol[lSC[0].toInt()][lSC[1].toInt()] = lSC[2].toInt()
        }

        // Assegniamo la soluzione alla variabile globale `solution`.
        solution = sol

        // Aggiorniamo la difficoltà del gioco.
        diff.value = sudoku.diff

        // Gestiamo gli errori e lo stato delle note.
        val mistakes = rememberSaveable { mutableIntStateOf(sudoku.mistakes) }
        val note = rememberSaveable { mutableStateOf(false) }

        // Restituiamo l'oggetto Game che rappresenta lo stato del gioco corrente.
        return Game(diff.value,  mistakes, sudoku.time, getSudoku(), NumberBar(), counter, numb = numbs, solution = solutionArray, note = Action(0, 0, note))
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
