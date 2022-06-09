package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.model.Score

@Dao
interface GameDao {

    @Insert
    fun insertSudoku(sudoku: SavedSudoku)

    @Insert
    fun insertScore(score: Score)

    @Delete
    fun deleteScore(score: Score)

    @Delete
    fun deleteSudoku(sudoku: SavedSudoku)

    @Query("SELECT * FROM scores WHERE difficulty = :diff")
    fun findDiffScore(diff: String): List<Score>

    @Query("DELETE FROM scores WHERE difficulty = :diff")
    fun deleteDiffScore(diff: String)

    @Query("SELECT * FROM scores")
    fun getAllScore(): LiveData<List<Score>>

    @Query("SELECT * FROM sudoku WHERE gameId = :id")
    fun findSudoku(id: Int): List<SavedSudoku>

    @Query("DELETE FROM sudoku WHERE gameId = :id")
    fun deleteSudoku(id: Int)

    @Query("SELECT * FROM sudoku")
    fun getAllSudoku(): LiveData<List<SavedSudoku>>
}