package com.example.sudoku.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku.R
import com.example.sudoku.computation.Navigation
import com.example.sudoku.computation.Screen
import com.example.sudoku.ui.theme.mainTitle
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: Navigation) {
    val scale = remember { Animatable(0f) }

    // AnimationEffect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(3000)
        navController.setScreen(Screen.MAIN_SCREEN)
    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        LoadingScreen()
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
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
                color = Color.Blue,
                modifier = Modifier
                    .width(128.dp)
                    .padding(bottom = 16.dp)
            )

            Text(
                text = stringResource(id = R.string.sudoku),
                style = mainTitle.copy(color = MaterialTheme.colors.onSurface),
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

