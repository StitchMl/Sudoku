package com.example.sudoku.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sudoku.R


@Composable
fun RulesScreen (navController: NavController)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .fillMaxSize()
    )
    {
        Image(
            painter = painterResource(id = R.drawable.sudoku),
            contentDescription = "sudoku",
            modifier = Modifier
                .height(170.dp)
                .width(170.dp),
            alignment = Alignment.CenterEnd
        )
        Text(
            text = stringResource(R.string.rules01) + "\n" +
                    stringResource(R.string.rules02) + "\n" +
                    stringResource(R.string.rules03) + "\n" +
                    "\n" + stringResource(R.string.rules04) + "\n" +
                    "\n" + stringResource(R.string.rules05),
            style = TextStyle(
                color = Color.Black,
                fontSize = 13.sp
            )
        )

    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun RulesScreenPreview(){
    RulesScreen(rememberNavController())
}