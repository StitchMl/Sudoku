package com.example.sudoku.screen

/*
enum class GameInputMode {
    Selection,
    NoteTaking
}

data class GameState(
    //val board: Board = List(9) { row -> List(9) { col -> Cell(row, col, 0) } },
    // val selection: CellCoordinates? = null,
    val context: Context */
/*= LocalContext.current*//*
,
    val diff: Array<String> = context.resources.getStringArray(R.array.difficulty),
    val mistakes: Int = 0,
    val elapsedTime: Long = 0,
    val inputMode: GameInputMode = GameInputMode.Selection,
){

    val elapsedTimeString: String
        get() {
            val sec = elapsedTime / 1000 % 60
            val min = elapsedTime / 1000 / 60

            return "${min.toString().padStart(2, '0')}:${sec.toString().padStart(2, '0')}"
        }

    val isGameOver: Boolean
        get() = mistakes >= 3

    //val isGameComplete: Boolean = board.flatten().all { it.correctValue == it.selection }

    val missingNumbers: Set<Int> = board
        .flatten()
        .filter { it.correctValue == it.selection }
        .groupBy { it.selection }
        .mapValues { it.value.size }
        .filter { it.value < 9 }
        .map { (key, _) -> key }
        .filterNotNull()
        .toSet()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (board != other.board) return false
        if (selection != other.selection) return false
        if (context != other.context) return false
        if (!diff.contentEquals(other.diff)) return false
        if (mistakes != other.mistakes) return false
        if (elapsedTime != other.elapsedTime) return false
        if (inputMode != other.inputMode) return false
        if (elapsedTimeString != other.elapsedTimeString) return false
        if (isGameOver != other.isGameOver) return false
        if (isGameComplete != other.isGameComplete) return false
        if (missingNumbers != other.missingNumbers) return false

        return true
    }



@Composable
fun GameInformationRow(state: GameState) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // Difficoltà (?)
            Text(text = "Mistakes ${state.mistakes}/3")
            Text(text = state.elapsedTimeString)
        }
    }
}

    override fun hashCode(): Int {
        var result = board.hashCode()
        result = 31 * result + (selection?.hashCode() ?: 0)
        result = 31 * result + context.hashCode()
        result = 31 * result + diff.contentHashCode()
        result = 31 * result + mistakes
        result = 31 * result + elapsedTime.hashCode()
        result = 31 * result + inputMode.hashCode()
        result = 31 * result + isGameComplete.hashCode()
        result = 31 * result + missingNumbers.hashCode()
        return result
    }
}


*/
