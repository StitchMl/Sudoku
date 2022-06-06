package com.example.sudoku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sudoku.model.Game
import com.example.sudoku.model.Score

@Database(entities = [(Game::class), (Score::class)], version = 1,
    exportSchema = false)
abstract class GameRoomDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {

        private var INSTANCE: GameRoomDatabase? = null

        fun getInstance(context: Context): GameRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameRoomDatabase::class.java,
                        "game_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}