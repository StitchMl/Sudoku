package com.example.sudoku.computation

import android.content.Context
import android.widget.Toast

internal fun Context.makeShortToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

internal fun Long.toTime(): String {
    if (this >= 3600) return "+59:59"
    var minutes = ((this % 3600) / 60).toString()
    if (minutes.length == 1) minutes = "0$minutes"
    var seconds = (this % 60).toString()
    if (seconds.length == 1) seconds = "0$seconds"


    return String.format("$minutes:$seconds")
}
