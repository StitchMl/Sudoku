package com.example.sudoku

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
import com.example.sudoku.screen.FirstScreen
import com.example.sudoku.screen.SplashScreen
import com.example.sudoku.ui.theme.SudokuEIlCaliceDiAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val diff = rememberSaveable{ mutableStateOf(0) }
            SudokuEIlCaliceDiAndroidTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    Navigation(diff)
                }

            }
        }
    }
}

@Composable
fun Navigation(diff: MutableState<Int>) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        // Main Screen
        composable("main_screen") {
            FirstScreen(navController, diff)
        }
        // Game Screen
        composable("new_game_screen"){
            /*TODO NewGameScreen(navController, diff)*/
        }
        composable("load_game_screen"){
            /*TODO LoadGameScreen(navController)*/
        }
        // Rules Screen
        composable("rules_screen"){
            /*TODO RulesScreen(navController)*/
        }
        // Result Screen
        composable("result_screen"){
            /*TODO ResultScreen(navController)*/
        }
    }
}

