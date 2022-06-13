package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.sudoku.model.Score

@Dao
interface GameDao {

    @Insert
    fun insertScore(score: Score)

    @Delete
    fun deleteScore(score: Score)

    @Query("DELETE FROM scores WHERE scoreId = :id")
    fun deleteScore(id: Int)

    @Query("SELECT * FROM scores")
    fun getAllScore(): LiveData<List<Score>>
}