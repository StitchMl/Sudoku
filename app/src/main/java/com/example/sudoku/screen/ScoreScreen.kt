package com.example.sudoku.screen

import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ScoreScreen(model: ScoreViewModel) {
    val allScore by model.allScore.observeAsState(listOf())
    Log.d("ScoreScreen", "allScore = $allScore")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val str = stringResource(R.string.scores)
        Text(
            text = str,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp),
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )

        if (allScore.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.score_value))
            }
        } else {
            LazyColumn(
                Modifier.fillMaxWidth().padding(10.dp)
            ) {
                item {
                    // Header Card
                    Card(
                        backgroundColor = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(8.dp),
                        elevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(2.dp)
                        ) {
                            Text(
                                text = "N",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(5.dp),
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = stringResource(R.string.difficulty),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(5.dp),
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = stringResource(R.string.mistake),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(5.dp),
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = stringResource(R.string.time),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(5.dp),
                                color = MaterialTheme.colors.onBackground,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                // List of scores
                itemsIndexed(allScore) { _, score ->
                    RealTimeUpdateItemCard(score)
                }
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

    Card(
        backgroundColor = MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Text(
                text = "${id-1}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(5.dp),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                text = diff,
                modifier = Modifier
                    .weight(2f)
                    .padding(5.dp),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                text = "$mistakes",
                modifier = Modifier
                    .weight(2f)
                    .padding(5.dp),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                text = time,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(5.dp),
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(device = Devices.DEFAULT, showBackground = true)
@Composable
fun ScoreScreenPreview(){
    ScoreScreen(ScoreViewModel(LocalContext.current.applicationContext as Application))
}
