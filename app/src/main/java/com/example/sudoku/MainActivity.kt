package com.example.sudoku

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.sudoku.computation.Navigation
import com.example.sudoku.computation.Screen
import com.example.sudoku.computation.makeShortToast
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.ui.theme.SudokuEIlCaliceDiAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val empty = rememberSaveable{ mutableStateOf(0) }
            val diff = rememberSaveable{ mutableStateOf("") }
            val timer = rememberSaveable{ mutableStateOf(0L) }
            val newRecord = rememberSaveable{ mutableStateOf(false) }
            val start = rememberSaveable{ mutableStateOf(false) }
            val screen = rememberSaveable{ mutableStateOf(Screen.SPLASH_SCREEN) }
            val context = applicationContext
            val score = ScoreViewModel(context as Application)
            val nav = Navigation(empty, diff, timer, newRecord, screen, score, start, context)
            onBackPressedDispatcher.addCallback(this) {
                if(screen.value == Screen.NEW_GAME_SCREEN || screen.value == Screen.LOAD_GAME_SCREEN){
                    val str = getString(R.string.game_saved)
                    start.value = false
                    makeShortToast(str)
                    //nav.saveGame()
                }
                screen.value = Screen.MAIN_SCREEN
            }
            SudokuEIlCaliceDiAndroidTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    nav.GetScreen()
                }

            }
        }
    }
}