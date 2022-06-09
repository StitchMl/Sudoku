package com.example.sudoku.model

import androidx.annotation.NonNull
import androidx.compose.runtime.MutableState
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class NumberBar(
    var bar: Array<MutableState<Boolean>?> = Array(9){null},
    var select: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NumberBar

        if (!bar.contentEquals(other.bar)) return false
        if (select != other.select) return false

        return true
    }

    override fun hashCode(): Int {
        var result = bar.contentHashCode()
        result = 31 * result + select
        return result
    }

    override fun toString(): String {
        return super.toString()
    }
}
