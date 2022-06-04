package com.example.sudoku

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.screen.FirstScreen
import com.example.sudoku.screen.NewGameScreen
import com.example.sudoku.screen.RulesScreen
import com.example.sudoku.screen.SplashScreen
import com.example.sudoku.screen.VictoryScreen
import com.example.sudoku.ui.theme.SudokuEIlCaliceDiAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val empty = rememberSaveable{ mutableStateOf(0) }
            val diff = rememberSaveable{ mutableStateOf("") }
            val timer = rememberSaveable{ mutableStateOf(0L) }
            val newRecord = rememberSaveable{ mutableStateOf(false) }
            val sudo = Sudoku(9, empty, diff)
            val context = applicationContext
            SudokuEIlCaliceDiAndroidTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    Navigation(sudo, empty, diff, timer, newRecord, context)
                }

            }
        }
    }
}

@Composable
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
}

