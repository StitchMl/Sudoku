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
                    "Esiste una sola regola per giocare a Sudoku: bisogna riempire la scacchiera in modo tale che ogni riga, ogni colonna e ogni riquadro contengano i numeri dall’1 al 9. La condizione è che nessuna riga, nessuna colonna o riquadro presentino due volte lo stesso numero." + "\n" +
                    "Il consiglio: usare il ragionamento. Ogni numero inserito correttamente è un’informazione in più per trovare gli altri." + "\n" +
                    "\n" + "L’esempio: il quadrato riportato qui sopra lo usiamo come esempio per suggerire alcune tecniche logiche per iniziare a giocare. Osservate i 7 nella serie verticale a sinistra dei tre riquadri. C’è un 7 nel riquadro superiore, un 7 nel riquadro inferiore, ma non c'è il 7 in quello centrale. Il 7 nel riquadro in alto vale per tutta la prima colonna, il 7 nel riquadro in basso vale anche per tutta la seconda colonna. Quindi il 7 che dobbiamo mettere nel riquadro centrale non andrà nella prima e nella seconda colonna, ma nella terza. All’interno del riquadro centrale, la terza colonna ha già due caselle compilate e una sola libera. Quella casella (contrassegnata nell’esempio con la lettera A) è l’unica che può contenere il 7. Ora vediamo i 7 nella serie di caselle in alto nella griglia. Il 7 c’è nel riquadro a sinistra e c’è anche in quello a destra, manca nel riquadro centrale. Il 7 del riquadro a destra vale per tutta la prima fila, il 7 del riquadro a sinistra vale per tutta la seconda fila di caselle. Usando la tecnica logica precedente sappiamo che in questo caso il 7 deve essere inserito nella casella B o nella casella C. Ma nella prima colonna del secondo riquadro c’è un 7, mentre non c’è nella seconda. Di conseguenza il 7 va messo dove si trova la lettera C. Andando avanti e prendendo in esame i riquadri centrali e sempre il numero 7 vediamo che manca nel riquadro a destra della sequenza di mezzo. Secondo gli stessi ragionamenti già fatti in precedenza, è chiaro che il 7 va dove abbiamo messo la lettera D." + "\n" +
                    "\n" + "Gli strumenti: matita,una gomma per cancellare gli eventuali errori, info per suggerimenti e torna indietro per eliminare l'ultimo numero inserito. Partite dai numeri che sono più presenti e ricordate che per ogni gioco la soluzione è una e una sola.",
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