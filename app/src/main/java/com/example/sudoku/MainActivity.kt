package com.example.sudoku

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
import com.example.sudoku.computation.MyTimerTask
import com.example.sudoku.computation.Navigation
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.ui.theme.SudokuEIlCaliceDiAndroidTheme
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val empty = rememberSaveable{ mutableStateOf(0) }
            val diff = rememberSaveable{ mutableStateOf("") }
            val timer = rememberSaveable{ mutableStateOf(0L) }
            val newRecord = rememberSaveable{ mutableStateOf(false) }
            val start = rememberSaveable{ mutableStateOf(false) }
            val screen = rememberSaveable{ mutableStateOf(0) }
            val sudo = Sudoku(9, empty, diff)
            val context = applicationContext
            val nav = Navigation(sudo, empty, diff, timer, newRecord, screen, start, context)
            val t = Timer()
            val task = MyTimerTask(timer, start)
            t.scheduleAtFixedRate(task, 1000, 1000)
            onBackPressedDispatcher.addCallback(this) {
                screen.value = 1
            }
            SudokuEIlCaliceDiAndroidTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    when (screen.value) {
                        0 -> { nav.Start() }
                        1 -> { nav.Main_screen() }
                        2 -> { nav.New_game_screen() }
                        3 -> { nav.Victory() }
                        4 -> { nav.Fail() }
                        5 -> { nav.Load_game_screen() }
                        6 -> { nav.Rules_screen() }
                        7 -> { nav.Result_screen() }
                    }
                }

            }
        }
    }
}

/*@Composable
fun Navigation(s: Sudoku, empty: MutableState<Int>, diff: MutableState<String>,
               timer: MutableState<Long>, newRecord: MutableState<Boolean>, context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        // Main Screen
        composable("main_screen") {
            FirstScreen(navController, empty, diff, s)
        }
        // Game Screen
        composable("new_game_screen"){
            NewGameScreen(navController, s.getGame(), timer, newRecord, context)
        }
        composable("victory"){
            VictoryScreen(navController, timer, newRecord)
        }
        composable("fail"){
            FailureScreen(navController, timer, newRecord)
        }
        composable("load_game_screen"){
            /*TODO LoadGameScreen(navController)*/
        }
        // Rules Screen
        composable("rules_screen"){
            RulesScreen(navController)
        }
        // Result Screen
        composable("result_screen"){
            /*TODO ResultScreen(navController)*/
        }
    }
}*/

