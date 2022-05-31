package com.example.sudoku.screen

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sudoku.R
import com.example.sudoku.model.Setting
import com.example.sudoku.ui.theme.ButtonColor
import com.example.sudoku.ui.theme.textColorLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun FirstScreen(navController: NavController, diff: MutableState<Int>){
    val scope = rememberCoroutineScope()
    when (diff.value) {
        0 -> { Screen(scope, diff) }
        61 -> { navController.navigate("load_game_screen") }
        62 -> { navController.navigate("rules_screen") }
        63 -> { navController.navigate("result_screen") }
        else -> { navController.navigate("new_game_screen") }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Screen(scope: CoroutineScope, diff: MutableState<Int>){
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val context = LocalContext.current
    val set = Setting(context)
    BottomDrawer(
        modifier = Modifier.background(MaterialTheme.colors.onPrimary),
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(scope, drawerState, diff, set)
        },
        gesturesEnabled = false
    ) {
        PrincipalScreen(scope, drawerState, diff, set)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawerContent(scope: CoroutineScope, drawerState: BottomDrawerState,
                  d: MutableState<Int>, set: Setting){
    val diff: Array<String> = set.DIFFICULTY
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.difficut), fontSize = 40.sp)
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (state in 1 until diff.size) {
                Row(modifier = Modifier
                    .clickable {
                        set.setDifficult(diff[state], d)
                    }
                    .padding(3.dp)) {
                    Column (horizontalAlignment = Alignment.Start) {
                        Text(text = diff[state],
                            modifier = Modifier.padding(horizontal = 10.dp))
                    }
                    Column(horizontalAlignment = Alignment.Start) {
                        Row(modifier = Modifier.padding(3.dp)) {
                            for (i in 1 until state + 1) {
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
fun PrincipalScreen(scope: CoroutineScope, drawerState: BottomDrawerState,
                    diff: MutableState<Int>, set: Setting){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { set.setDifficult("Result", diff) }) {
                Icon(ImageVector.vectorResource(R.drawable.trophy),
                    "") } },
        isFloatingActionButtonDocked = true
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(R.string.sudoku), fontSize = 70.sp)
            Text(stringResource(R.string.group), fontSize = 14.sp)
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
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor, textColorLight)
            ) {
                Text(stringResource(R.string.new_game), fontSize = 22.sp)
            }
            Button(
                onClick = { set.setDifficult("Continue", diff) },
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
                onClick = { set.setDifficult("Rules", diff) },
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

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun FirstScreenPreview(){
    val scope = rememberCoroutineScope()
    val diff = rememberSaveable { mutableStateOf(0) }
    Screen(scope, diff)
}