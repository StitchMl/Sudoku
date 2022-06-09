package com.example.sudoku.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "scores")
class Score (
    @ColumnInfo(name = "difficulty")
    var diff: String = "",
    @ColumnInfo(name = "mistakes")
    var mistakes: Int = 0,
    @ColumnInfo(name = "time")
    var time: Long = 0L
): Parcelable{
    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "scoreId")
    var id: Int = 0
}
