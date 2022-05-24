package com.example.sudoku.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sudoku.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun FirstScreen(){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
    val diff: Array<String> = context.resources.getStringArray(R.array.difficulty)
    BottomDrawer(
        modifier = Modifier.background(MaterialTheme.colors.onPrimary),
        drawerState = drawerState,
        drawerContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.difficut), fontSize = 40.sp)
                Column {
                    for (state in 1 until diff.size) {
                        Row(modifier = Modifier
                            .clickable { }
                            .padding(3.dp)) {
                            Column (horizontalAlignment = Alignment.Start) {
                                Text(text = diff[state], modifier = Modifier.padding(horizontal = 10.dp))
                            }
                            Column(horizontalAlignment = Alignment.Start) {
                                Row(modifier = Modifier
                                    .padding(3.dp)) {
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
        },
        gesturesEnabled = false
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
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
                    Text(stringResource(R.string.new_game), fontSize = 22.sp)
                }
                Button(
                    onClick = {
                        //TODO
                    },
                    // Uses ButtonDefaults.ContentPadding by default
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 10.dp,
                        end = 20.dp,
                        bottom = 10.dp
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .width(160.dp)
                        .height(40.dp)
                ) {
                    Text(stringResource(R.string.load_game), fontSize = 13.sp)
                }
            }
        }
    }
}