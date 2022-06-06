package com.example.sudoku.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sudoku.model.Game
import com.example.sudoku.model.Score

@Dao
interface GameDao {

    @Insert
    fun insertGame(game: Game)

    @Insert
    fun insertScore(score: Score)

    @Query("SELECT * FROM games WHERE gameId = :id")
    fun findGame(id: Int): List<Game>

    @Query("DELETE FROM games WHERE gameId = :id")
    fun deleteGame(id: Int)

    @Query("SELECT * FROM games")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT scoreId, difficulty, mistakes, time FROM scores")
    fun getAllScore(): LiveData<List<Score>>
}