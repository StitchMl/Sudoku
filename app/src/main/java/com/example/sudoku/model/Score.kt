package com.example.sudoku.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "scores",
    foreignKeys = [
        ForeignKey(
            entity = Game::class,
            parentColumns = ["gameId"],
            childColumns = ["scoreId"]),
        ForeignKey(
            entity = Game::class,
            parentColumns = ["diff"],
            childColumns = ["difficulty"]),
        ForeignKey(
            entity = Game::class,
            parentColumns = ["mistakes"],
            childColumns = ["mistakes"]),
        ForeignKey(
            entity = Game::class,
            parentColumns = ["time"],
            childColumns = ["time"])
    ]
)
class Score (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "scoreId")
    var id: Int = 0,
    @ColumnInfo(name = "difficulty")
    var diff: String = "",
    @ColumnInfo(name = "mistakes")
    var mistakes: Int = 0,
    @ColumnInfo(name = "time")
    var time: Long = 0L
): Parcelable
