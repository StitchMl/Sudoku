package com.example.sudoku.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import com.example.sudoku.model.Score
import com.example.sudoku.model.ScoreViewModel


/*fun ScoreScreen(navController: NavController, list: List<Score>) {

    val readlist : List<Score> = list //read-only view of the list



    //Adds a new item to the list
    fun AddList(new : Score)
    {
        list.add(new)
    }

    //returns an immutable List
    fun getList() : List <Score>
    {
        return list
    }

    fun printList() : List <Score>
    {
        getList().forEach{
            i -> println("$i")
        }

    }
}*/

@Composable
fun ScoreScreen( score: ScoreViewModel)
{
    val list = score.realTimeUpdateItem.collectAsState()
    //val mistake = rememberSaveable{ mutableStateOf(score.mistakes) }
    //val time = rememberSaveable{ mutableStateOf(score.time) }
    LazyColumn {
        itemsIndexed(list.value) { index, item ->
            RealTimeUpdateItemCard(
                score = item
            )
        }
    }
}

@Composable
fun RealTimeUpdateItemCard(
    score: Score
) {
    val time = remember { score.time }
    val black = Color.Black
    val white = Color.White
   /* val green = Color(
        ContextCompat.getColor(
            LocalContext.current,
            R.color.opacity_green
        )
    )*/
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
                text = "${time}",
                modifier = Modifier.padding(16.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Box(
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

            }
        }
    }
}
