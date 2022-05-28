package com.example.sudoku.screen


import kotlin.random.Random

//La classe rappresenta una griglia sudoku  ovvero una matrice 9x9 contenente 9 celle di dim. 3x3
class Grid private constructor(private val grid: Array<Array<Cell?>>) {
    //ritorna la grandezza della grigia che contiene celle di dim (size x size)
    val size: Int
        get() = grid.size

    //ritorna la cella in una data posizione nella griglia
    //(righe e colonne partono dalla posizione 0)
    fun getCell(row: Int, column: Int): Cell? {
        return grid[row][column] //dove row e column sono la riga e la colonna contenete quella cella
    }

    //controllo se il valore immesso è valido e contiene effettivammente una cella
    //un valore è valido se non esiste più volte uguale nella stessa riga, colonnna e nell'intea griglia
    fun isValidValueForCell(cell: Cell, value: Int): Boolean {
        //parametri: cella da controllare, valore immesso da controllare
        return isValidInRow(cell, value) && isValidInColumn(cell, value) && isValidInBox(
            cell,
            value
        )
    } //ritorna vero se il valore è valido, falso altrimenti

    private fun isValidInRow(cell: Cell, value: Int): Boolean {
        return !getRowValuesOf(cell).contains(value)
    }

    private fun isValidInColumn(cell: Cell, value: Int): Boolean {
        return !getColumnValuesOf(cell).contains(value)
    }

    private fun isValidInBox(cell: Cell, value: Int): Boolean {
        return !getBoxValuesOf(cell).contains(value)
    }

    private fun getRowValuesOf(cell: Cell): Collection<Int> {
        val rowValues: MutableList<Int> = ArrayList()
        for (neighbor in cell.rowNeighbors!!) rowValues.add(neighbor.value)
        return rowValues
    }

    private fun getColumnValuesOf(cell: Cell): Collection<Int> {
        val columnValues: MutableList<Int> = ArrayList()
        for (neighbor in cell.columnNeighbors!!) columnValues.add(neighbor.value)
        return columnValues
    }

    private fun getBoxValuesOf(cell: Cell): Collection<Int> {
        val boxValues: MutableList<Int> = ArrayList()
        for (neighbor in cell.boxNeighbors!!) boxValues.add(neighbor.value)
        return boxValues
    }

    //ritorna la prima cella vuota della griglia
    //ritorna un valore (non nullo) contenente la prima cella vuota (se è presente)
    val firstEmptyCell: Cell?
        get() {
            val firstCell = grid[0][0] ?: return null
            return if (firstCell.isEmpty) {
                firstCell
            } else getNextEmptyCellOf(firstCell)
        }

    //ritorna la seconda cella vuota successiva a quella di prima
    //parametri: cella di cui si dovrebbe ottenere la successiva vuota
    //ritorna un valore (non nullo) contenete la successiva cella vuota, se presente
    private fun getNextEmptyCellOf(cellValue: Cell?): Cell? {
        var cell = cellValue
        var nextEmptyCell: Cell? = null
        while (cell!!.nextCell.also { cell = it } != null) {
            if (!cell!!.isEmpty) {
                continue
            }
            nextEmptyCell = cell
            break
        }
        return nextEmptyCell
    }

    //ritorna una rappresentazione in stringa della griglia
    override fun toString(): String {
        return StringConverter.toString(this)
    }

    //classe che rappresenta una cella nella griglia.
    class Cell(
        //uso questo metodo perchè mi permette di cambiare il valore della cella
        //il parametro è il valore della cella
        var value: Int
    ) {
        //ritorna il valore della cella che è una cifra da 1 a 9 o 0 (se la cella è vuota)
        // ritorna una collezione di altre celle nella stessa riga della cella (rowNeighbors)
        var rowNeighbors: Collection<Cell>? = null

        //ritorna una collezione di altre celle nella stessa colonna della cella
        var columnNeighbors: Collection<Cell>? = null

        //ritorna una collezione di altre celle nello stesso box di queste celle
        var boxNeighbors: Collection<Cell>? = null

        //ritorna la successiva cella di questa cella :)
        //questa funzione ritorna la cella a destra di ogni cella se essa non è l'ultima della riga
        //se è l'ultima ritorna la prima cella nella successiva riga
        //per l'ultima cella rimanente ritorna un valore null
        //nexCell è la cella consecutiva a questa cella
        var nextCell: Cell? = null

        //indica se la cella è vuota (ritorna true) o meno (ritorna falso)
        val isEmpty: Boolean
            get() = value == 0
    }

    private object StringConverter {
        fun toString(grid: Grid): String {
            val builder = StringBuilder()
            val size = grid.size
            printTopBorder(builder)
            for (row in 0 until size) {
                printRowBorder(builder)
                for (column in 0 until size) {
                    printValue(builder, grid, row, column)
                    printRightColumnBorder(builder, column + 1, size)
                }
                printRowBorder(builder)
                builder.append("\n")
                printBottomRowBorder(builder, row + 1, size)
            }
            printBottomBorder(builder)
            return builder.toString()
        }

        //metodo figo, lo lascio (>_<)
        private fun printTopBorder(builder: StringBuilder) {
            builder.append("╔═══╤═══╤═══╦═══╤═══╤═══╦═══╤═══╤═══╗\n")
        }

        private fun printRowBorder(builder: StringBuilder) {
            builder.append("║")
        }

        private fun printValue(builder: StringBuilder, grid: Grid, row: Int, column: Int) {
            val value = grid.getCell(row, column)!!.value
            val output = if (value != 0) value.toString() else " "
            builder.append(" $output ")
        }

        private fun printRightColumnBorder(builder: StringBuilder, column: Int, size: Int) {
            if (column == size) {
                return
            }
            if (column % 3 == 0) {
                builder.append("║")
            } else {
                builder.append("│")
            }
        }

        private fun printBottomRowBorder(builder: StringBuilder, row: Int, size: Int) {
            if (row == size) {
                return
            }
            if (row % 3 == 0) {
                builder.append("╠═══╪═══╪═══╬═══╪═══╪═══╬═══╪═══╪═══╣\n")
            } else {
                builder.append("╟───┼───┼───╫───┼───┼───╫───┼───┼───╢\n")
            }
        }

        private fun printBottomBorder(builder: StringBuilder) {
            builder.append("╚═══╧═══╧═══╩═══╧═══╧═══╩═══╧═══╧═══╝\n")
        }
    }

    companion object {

        //ritorna una griglia di un array di due numeri
        fun of(grid: Array<IntArray>): Grid {
            verifyGrid(grid)
            val cells = Array(9) { arrayOfNulls<Cell>(9) }
            val rows: MutableList<MutableList<Cell>> = ArrayList()
            val columns: MutableList<MutableList<Cell>> = ArrayList()
            val boxes: MutableList<MutableList<Cell>> = ArrayList()
            for (i in 0..8) {
                rows.add(ArrayList())
                columns.add(ArrayList())
                boxes.add(ArrayList())
            }
            var lastCell: Cell? = null
            for (row in grid.indices) {
                for (column in 0 until grid[row].size) {
                    val cell = Cell(grid[row][column])
                    cells[row][column] = cell
                    rows[row].add(cell)
                    columns[column].add(cell)
                    boxes[row / 3 * 3 + column / 3].add(cell)
                    if (lastCell != null) {
                        lastCell.nextCell = cell
                    }
                    lastCell = cell
                }
            }
            for (i in 0..8) {
                val row: List<Cell> = rows[i]
                for (cell in row) {
                    val rowNeighbors: MutableList<Cell> = ArrayList(row)
                    rowNeighbors.remove(cell)
                    cell.rowNeighbors = rowNeighbors
                }
                val column: List<Cell> = columns[i]
                for (cell in column) {
                    val columnNeighbors: MutableList<Cell> = ArrayList(column)
                    columnNeighbors.remove(cell)
                    cell.columnNeighbors = columnNeighbors
                }
                val box: List<Cell> = boxes[i]
                for (cell in box) {
                    val boxNeighbors: MutableList<Cell> = ArrayList(box)
                    boxNeighbors.remove(cell)
                    cell.boxNeighbors = boxNeighbors
                }
            }
            return Grid(cells)
        }

        //ritorna una grigli vuota
        fun emptyGrid(): Grid {
            val emptyGrid = Array(9) { IntArray(9) }
            return of(emptyGrid)
        }

        private fun verifyGrid(grid: Array<IntArray>?) {
            requireNotNull(grid) { "grid must not be null" }
            require(grid.size == 9) { "grid must have nine rows" }
            for (row in grid) {
                require(row.size == 9) { "grid must have nine columns" }
                for (value in row) {
                    require(!(value < 0 || value > 9)) { "grid must contain values from 0-9" }
                }
            }
        }
    }
}

//-----------------------------------------------------------------------------------------

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


data class Cell(
    val row: Int,
    val col: Int,
    val correctValue: Int,
    val selection: Int? = null,
    val notes: Set<Int> = emptySet(),
    val preset: Boolean = false
) {
    val isMistake: Boolean
        get() = selection != null && selection != correctValue
}

class SudokuGameGenerator {

    fun generateBoard(emptyCells: Int = 42): List<List<Cell>> {
        val generator = Generator()
        val grid = generator.generateFullSolution()

        val solution = List(9) { row ->
            List(9) { col ->
                grid.getCell(row, col)!!.value
            }
        }

        generator.eraseCells(grid, emptyCells)

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

}