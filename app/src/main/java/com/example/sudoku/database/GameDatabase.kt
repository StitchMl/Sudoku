package com.example.sudoku.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sudoku.computation.Converters
import com.example.sudoku.model.SavedSudoku
import com.example.sudoku.model.Score

@Database(entities = [(Score::class), (SavedSudoku::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GameDatabase: RoomDatabase() {

    abstract fun gameDao(): GameDao

    companion object {
        private var INSTANCE: GameDatabase? = null

        fun getInstance(context: Context): GameDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameDatabase::class.java,
                        "game_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}