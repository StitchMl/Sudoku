package com.example.sudoku.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scores")
class Score (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "scoreId")
    var id: Int = 0,
    @ColumnInfo(name = "difficulty")
    var diff: String,
    @ColumnInfo(name = "mistakes")
    var mistakes: Int,
    @ColumnInfo(name = "time")
    var time: Long,
    var numb: String = "",
    var sudoku: String = "",
    var solution: String = "",
    var counter: Int = 0
)
