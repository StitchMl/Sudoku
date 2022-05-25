package com.example.sudoku.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku.R
import com.example.sudoku.ui.theme.Purple700
import com.example.sudoku.ui.theme.SudokuEIlCaliceDiAndroidTheme
import com.example.sudoku.ui.theme.mainTitle

@Preview(device = Devices.PIXEL_3)
@Composable
fun LoadingPreview() {
    SudokuEIlCaliceDiAndroidTheme {
            LoadingScreen()
    }
}

@Composable
fun LoadingScreen() {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                modifier = Modifier
                    .size(200.dp),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )

            LinearProgressIndicator(
                color = Purple700,
                modifier = Modifier
                    .width(128.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(id = R.string.sudoku),
                style = mainTitle.copy(color = MaterialTheme.colors.primary),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

