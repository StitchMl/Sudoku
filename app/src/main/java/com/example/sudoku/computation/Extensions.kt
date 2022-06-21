package com.example.sudoku.computation

import android.content.Context
import android.widget.Toast


/*internal fun Context.makeLongToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_LONG
    ).show()
}*/

internal fun Context.makeShortToast(message: String) {
    Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()
}

/*var minuti = mutableStateOf(0)

internal fun Long.toTime(): String {
   //var minuti = mutableStateOf(0)
    //var minuti = Int
    //minuti.value = 0
    //if (this >= 60*3600) return "+59:59"
    if(minuti.value >= 60) return "+59:59"
    //var minutes = ((this % (3600)) / 60).toString()
    //var minutes = ((this % (3600/9)) / 60).toString() //minuti
    //if (minutes.length == 1) minutes = "0$minutes"
    //var seconds = (this % 60).toString()

    var seconds = ((this % 3600) / 60).toString() //secondi vanno bene
    //var seconds = ((this % (3600/2)) / 60).toString()
    if (seconds.length == 1) seconds = "0$seconds"

    if("$seconds".toInt() == 59) {
        minuti.value = minuti.value+1
       // minuti = minuti + 1
    }

    return String.format("${minuti.value.toString()}:$seconds")
    //return String.format("$minutes:$seconds")
}


//var minutes = ((this % (3600/2)) / 60).toString()*/
internal fun Long.toTime(): String {
    if (this >= 3600) return "+59:59"
    var minutes = ((this % 3600) / 60).toString()
    if (minutes.length == 1) minutes = "0$minutes"
    var seconds = (this % 60).toString()
    if (seconds.length == 1) seconds = "0$seconds"


    return String.format("$minutes:$seconds")
}
