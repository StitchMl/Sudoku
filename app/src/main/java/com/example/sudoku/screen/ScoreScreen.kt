package com.example.sudoku.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sudoku.computation.toTime
import com.example.sudoku.database.ScoreViewModel
import com.example.sudoku.model.Score


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
fun ScoreScreen(score: ScoreViewModel)
{
    //val list = score.realTimeUpdateItem.collectAsState()
    val list = score.allScore
    println(list.value)
    //val mistake = rememberSaveable{ mutableStateOf(score.mistakes) }
    //val time = rememberSaveable{ mutableStateOf(score.time) }
    if(list.value != null) {
        LazyColumn {
            itemsIndexed(list.value!!) { _, item ->
                RealTimeUpdateItemCard(
                    score = item
                )
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
            Text(text = "There's not any result yet")
        }
    }
}

@Composable
fun RealTimeUpdateItemCard(score: Score) {
    val time = remember { score.time.toTime() }
    val diff = remember { score.diff }
    val mistakes = remember { score.mistakes }
    //val black = Color.Black
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
                text = "$diff $mistakes $time",
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

