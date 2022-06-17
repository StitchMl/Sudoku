package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sudoku.model.Score

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScore(score: Score)

    @Delete
    fun deleteScore(score: Score)

    @Query("DELETE FROM scores WHERE scoreId = :id")
    fun deleteScoreById(id: Int)

    @Query("SELECT * FROM scores")
    fun getAllScore(): LiveData<List<Score>>
}