package com.example.sudoku.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sudoku.model.Game


//funzione per inserire difficolta', errori, timer

@Composable
fun CurrentInfoBar(g: Game, context: Context) {
    CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.caption) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = g.difficult.value,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            Text(text = "Errori: ${g.mistakes}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
            Text(text = g.elapsedTimeString,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
        }
    }
}

