package com.example.sudoku.`class`

import com.example.sudoku.screen.Board
import com.example.sudoku.screen.GameInputMode

data class GameState(
    val board: Board = List(9) { row -> List(9) { col -> Cell(row, col, 0) } },
   // val selection: CellCoordinates? = null,
    //val difficulty: GameDifficulty = GameDifficulty.Medium,
    val mistakes: Int = 0,
    val elapsedTime: Long = 0,
    val inputMode: GameInputMode = GameInputMode.Selection,
)//: //MavericksState
{
    //tempo
    val elapsedTimeString: String
        get() {
            val secs = elapsedTime / 1000 % 60
            val mins = elapsedTime / 1000 / 60

            return "${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}"
        }

    val isGameOver: Boolean
        get() = mistakes >= 3

    //problemi con it
    //val isGameComplete: Boolean = board.flatten().all { it.correctValue == it.selection }

   /* val missingNumbers: Set<Int> = board
        .flatten()
        .filter { it.correctValue == it.selection }
        .groupBy { it.selection }
        .mapValues { it.value.size }
        .filter { it.value < 9 }
        .map { (key, _) -> key }
        .filterNotNull()
        .toSet()*/
}


enum class GameInputMode {
    Selection,
    NoteTaking
}
