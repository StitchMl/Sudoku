package com.example.sudoku.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import kotlinx.coroutines.launch

//@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun FirstScreen(){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* ... */ }) {
                Icon(ImageVector.vectorResource(R.drawable.trophy), "")
            }
        },
        // Defaults to false
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                // Defaults to null, that is, No cutout
                cutoutShape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50)
                )
            ) {
                /* Bottom app bar content */
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sudoku", fontSize = 70.sp)
            Text("Il calice di Android", fontSize = 14.sp)
            Spacer(modifier = Modifier.size(80.dp))
            Button(
                onClick = { /* ... */ },
                // Uses ButtonDefaults.ContentPadding by default
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                modifier = Modifier
                    .width(190.dp)
                    .height(50.dp)
            ) {
                Text("Nuova partita", fontSize = 22.sp)
            }
            Button(
                onClick = { /* ... */ },
                // Uses ButtonDefaults.ContentPadding by default
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                ),
                modifier = Modifier
                    .padding(10.dp)
                    .width(160.dp)
                    .height(40.dp)
            ) {
                Text("Continua partita", fontSize = 14.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun FirstScreenProva(){
    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    BottomDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Drawer content
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { /* ... */ }) {
                    Icon(ImageVector.vectorResource(R.drawable.trophy), "")
                }
            },
            // Defaults to false
            isFloatingActionButtonDocked = true
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Sudoku", fontSize = 70.sp)
                Text("Il calice di Android", fontSize = 14.sp)
                Spacer(modifier = Modifier.size(70.dp))
                Button(
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    // Uses ButtonDefaults.ContentPadding by default
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    modifier = Modifier
                        .width(190.dp)
                        .height(50.dp)
                ) {
                    Text("Nuova partita", fontSize = 22.sp)
                }
                Button(
                    onClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    // Uses ButtonDefaults.ContentPadding by default
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(160.dp)
                        .height(40.dp)
                ) {
                    Text("Continua partita", fontSize = 13.sp)
                }
            }
        }
    }
}