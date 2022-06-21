package com.example.sudoku.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku.R
import com.example.sudoku.computation.toTime
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Score

@Composable
fun ScoreScreen(model: ScoreViewModel)
{
    val allScore by model.allScore.observeAsState(listOf())
    println(allScore)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val str = stringResource(R.string.scores)
        Text(
            text = str,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        LazyColumn(
            Modifier.fillMaxWidth().padding(10.dp)
        ) {
            itemsIndexed(allScore) { i, score ->
                if(allScore[i].id != 1) {
                    RealTimeUpdateItemCard(
                        score = score
                    )
                }
            }
        }

        if (allScore.size < 2 && (allScore.isEmpty() || allScore[0].id==1)){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "There's not any result yet")
            }
        }
    }
}

@Composable
fun RealTimeUpdateItemCard(score: Score) {
    val id = remember { score.id }
    val time = remember { score.time.toTime() }
    val diff = remember { score.diff }
    val mistakes = remember { score.mistakes }
    val white = Color.White
    Card(
        backgroundColor = white,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            Text(
                text = "${id-1}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = diff,
                modifier = Modifier.padding(5.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$mistakes",
                modifier = Modifier.padding(5.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = time,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            /*Box(
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            ) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.CenterEnd),
                    //progress = animatedProgress,
                )

            }*/
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun ScoreScreenPreview(){
    ScoreScreen(ScoreViewModel(LocalContext.current.applicationContext as Application))
}
