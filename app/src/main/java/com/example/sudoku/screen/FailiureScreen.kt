package com.example.sudoku.screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku.R
import com.example.sudoku.computation.Navigation
import com.example.sudoku.computation.toTime
import com.example.sudoku.ui.theme.mainTitle
import com.example.sudoku.ui.theme.newGameSubtitle
import com.example.sudoku.ui.theme.textColorLight
import kotlinx.coroutines.delay

@Composable
fun FailureScreen(navController: Navigation,
                  timerState: MutableState<Long>,
                  isNewRecordState: MutableState<Boolean>
) {
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
        timerState.value = 0L
        isNewRecordState.value = false
        navController.setScreen(1)
    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        GameFailScreen(timerState.value)
    }
}

/** This screen represents when the user has completed a puzzle. */
@Composable
fun GameFailScreen(timerState: Long) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier.wrapContentSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                contentDescription = stringResource(R.string.game_complete),
                imageVector = ImageVector.vectorResource(R.drawable.close_circle_outline),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                modifier = Modifier.size(128.dp, 128.dp)
            )
        }

        Text(
            text = stringResource(R.string.game_over),
            style = mainTitle.copy(
                color = textColorLight,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = stringResource(R.string.total_time),
            style = newGameSubtitle.copy(
                color = textColorLight,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            text = timerState.toTime(),
            style = newGameSubtitle.copy(
                color = textColorLight,
                fontWeight = FontWeight.ExtraBold
            )
        )
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun ShowFailureScreen(){
    GameFailScreen(0L)
}