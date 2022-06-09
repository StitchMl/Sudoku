package com.example.sudoku.screen

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import com.example.sudoku.computation.Navigation
import com.example.sudoku.computation.Screen
import com.example.sudoku.computation.Setting
import com.example.sudoku.computation.Sudoku
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Game
import com.example.sudoku.ui.theme.ButtonColor
import com.example.sudoku.ui.theme.textColorLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun FirstScreen(navController: Navigation, empty: MutableState<Int>, diff: MutableState<String>, s: Sudoku){
    val scope = rememberCoroutineScope()
    Screen(scope, empty, diff, navController, s)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Screen(scope: CoroutineScope, empty: MutableState<Int>,
           diff: MutableState<String>, navController: Navigation, s: Sudoku){
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val context = LocalContext.current
    val set = Setting(context)
    BottomDrawer(
        modifier = Modifier.background(MaterialTheme.colors.onPrimary),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(scope, drawerState, empty, set, diff, navController, s)
        },
        gesturesEnabled = false
    ) {
        PrincipalScreen(scope, drawerState, navController)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerContent(scope: CoroutineScope, drawerState: BottomDrawerState,
                  e: MutableState<Int>, set: Setting, d: MutableState<String>,
                  navController: Navigation, s: Sudoku){
    val diff: Array<String> = set.DIFFICULTY
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.difficulty), fontSize = 40.sp)
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (state in diff.indices) {
                Row(modifier = Modifier
                    .clickable {
                        d.value = diff[state]
                        set.setDifficult(d.value, e)
                        s.changeGame()
                        navController.setScreen(Screen.NEW_GAME_SCREEN)
                    }
                    .padding(3.dp)) {
                    Column (horizontalAlignment = Alignment.Start) {
                        Text(text = diff[state],
                            modifier = Modifier.padding(horizontal = 10.dp))
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        Row(modifier = Modifier.padding(3.dp)) {
                            for (i in 0 until state + 1) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.star),
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 150.dp, end = 150.dp)
                )
            }
        }
        Row(modifier = Modifier
            .clickable {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }
            .padding(5.dp)){
            Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PrincipalScreen(
    scope: CoroutineScope, drawerState: BottomDrawerState,
    navController: Navigation
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.setScreen(Screen.RESULT_SCREEN) }) {
                Icon(ImageVector.vectorResource(R.drawable.trophy), "") } },
        isFloatingActionButtonDocked = true
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.sudoku), fontSize = 70.sp)
            Text(stringResource(R.string.group), fontSize = 14.sp)
            Image(
                painter = painterResource(id = R.drawable.ic_calice_logo),
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 8.dp) ,
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.size(70.dp))
            Button(
                onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                },
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                modifier = Modifier
                    .width(190.dp)
                    .height(53.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor, textColorLight)
            ) {
                Text(stringResource(R.string.new_game), fontSize = 22.sp)
            }
            Button(
                onClick = {
                    navController.setScreen(Screen.LOAD_GAME_SCREEN)
                          },
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 10.dp,
                    end = 20.dp,
                    bottom = 10.dp
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .width(160.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor, textColorLight)
            )
            {
                Text(stringResource(R.string.load_game), fontSize = 13.sp)
            }
            Button(
                onClick = { navController.setScreen(Screen.RULES_SCREEN) },
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 10.dp,
                    end = 20.dp,
                    bottom = 10.dp
                ),
                modifier = Modifier
                    .width(160.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor, textColorLight)
            ) {
                Text(stringResource(R.string.rules), fontSize = 13.sp)
            }
        }
    }
}


/*@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun FirstScreenPreview(){
    val scope = rememberCoroutineScope()
    val empty = rememberSaveable { mutableStateOf(0) }
    val screen = rememberSaveable { mutableStateOf(Screen.SPLASH_SCREEN) }
    val diff = rememberSaveable { mutableStateOf("") }
    val timer = rememberSaveable{ mutableStateOf(0L) }
    val newRecord = rememberSaveable{ mutableStateOf(false) }
    val start = rememberSaveable{ mutableStateOf(false) }
   val sudo = Sudoku(9, empty, diff)
    val context = LocalContext.current
    Screen(scope, empty, diff,
        Navigation(empty, diff, timer, newRecord, screen, start,allGames = List<Game> ,ScoreViewModel(LocalContext.current.applicationContext as Application), context),
        Sudoku(9, empty, diff))
}*/
