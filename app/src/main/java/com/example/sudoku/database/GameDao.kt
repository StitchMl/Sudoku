package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sudoku.model.SavedCell
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.model.Score

@Dao
interface GameDao {

    @Insert(entity = SavedSudoku::class)
    fun insertSudoku(sudoku: SavedSudoku)

    @Insert(entity = Score::class)
    fun insertScore(score: Score)

    @Insert(entity = SavedCell::class)
    fun insertCell(cell: SavedCell)

    @Delete
    fun deleteScore(score: Score)

    @Delete
    fun deleteSudoku(sudoku: SavedSudoku)

    @Delete
    fun deleteCell(cell: SavedCell)

    @Query("DELETE FROM scores WHERE difficulty = :diff")
    fun deleteDiffScore(diff: String)

    @Query("SELECT * FROM scores")
    fun getAllScore(): LiveData<List<Score>>

    @Query("DELETE FROM sudoku WHERE gameId = :id")
    fun deleteSudoku(id: Int)

    @Query("SELECT * FROM sudoku")
    fun getAllSudoku(): LiveData<List<SavedSudoku>>

    @Query("DELETE FROM cell WHERE `row` = :row AND col = :col")
    fun deleteCell(row: Int, col: Int)

    @Query("SELECT * FROM cell")
    fun getAllCell(): LiveData<List<SavedCell>>
}