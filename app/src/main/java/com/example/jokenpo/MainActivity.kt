package com.example.jokenpo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jokenpo.ui.theme.JokenpoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JokenpoTheme {
                JokenpoApp()
            }
        }
    }
}


@Preview
@Composable
fun JokenpoApp() {
    JokenpoImageAndButtons(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)

    )
}

@Composable
fun CustomImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageId: Int
) {

    val painter = rememberImagePainter(
        data = imageId,
    )

    Image(
        modifier = modifier
            .size(120.dp)
            .clickable { onClick() },
        painter = painter,
        contentDescription = imageId.toString()
    )
}

@Composable
fun JokenpoImageAndButtons(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var computerResult by remember { mutableStateOf(1) }
        var playerWins by remember { mutableStateOf(0) }


        /**
         * Trecho de código responsável por trocar a imagem da máquina e o texto da escolha da máquina
         */
        when (computerResult) {
            0 -> {
                Text(text = "Escolha da máquina: Pedra")
                CustomImage(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 32.dp),
                    imageId = R.drawable.pedra,
                    onClick = {})
            }
            1 -> {
                Text(text = "Escolha da máquina: Papel")
                CustomImage(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 32.dp),
                    imageId = R.drawable.papel,
                    onClick = {})
            }
            2 -> {
                Text(text = "Escolha da máquina: Tesoura")
                CustomImage(
                    modifier = Modifier
                        .size(180.dp)
                        .padding(bottom = 32.dp),
                    imageId = R.drawable.tesoura,
                    onClick = {})
            }
        }


        /**
         * Trecho de código responsável por trocar o texto a cada vitória ou derrota
         */
        when (playerWins) {
            Results.PLAYER_WIN -> {
                Text(color = Color.Green ,text = stringResource(id = R.string.win_game))
            }
            Results.PLAYER_LOSE -> {
                Text(color = Color.Red,text = stringResource(id = R.string.lose_game))
            }
            Results.SAME_RESULT -> {
                Text(color = Color.Black, text = stringResource(id = R.string.same_result))
            }
        }


        Spacer(modifier = Modifier.height(16.dp))


        /**
         * Campos para a escolha do jogador
         */
        Row(
            modifier = Modifier.padding(32.dp)
        ) {
            CustomImage(
                onClick = {
                    computerResult = generateRandomValue()
                    playerWins = checkWhoWins(computerResult, 0)
                },
                imageId = R.drawable.pedra
            )


            CustomImage(
                onClick = {
                    computerResult = generateRandomValue()
                    playerWins = checkWhoWins(computerResult, 1)
                },
                imageId = R.drawable.papel
            )

            CustomImage(
                onClick = {
                    computerResult = generateRandomValue()
                    playerWins = checkWhoWins(computerResult, 1)
                },
                imageId = R.drawable.tesoura
            )

        }


    }


}


private fun generateRandomValue(): Int {
    val random = (1..2).random()
    Log.d("TAG", "Gerando resultado aleatorio para a maquina: $random")
    return random
}

private fun checkWhoWins(computerResult: Int, playerChoice: Int): Int {

    /**
     * 0 = pedra
     * 1 = papel
     * 2 = tesoura
     *
     * 0 vence 2
     * 0 perde 1
     *
     * 1 vence 0
     * 1 perde 2
     *
     * 2 vence 1
     * 2 perde 0
     *
     */

    Log.d("TAG", "checando quem venceu: computador: $computerResult --- player: $playerChoice")
    if (playerChoice == computerResult) {
        return Results.SAME_RESULT
    }
    if (playerChoice == 1 && computerResult == 0) {
        return Results.PLAYER_WIN
    } else if (playerChoice == 1 && computerResult == 2) {
        return Results.PLAYER_LOSE
    } else if (playerChoice == 2 && computerResult == 1) {
        return Results.PLAYER_WIN
    } else if (playerChoice == 2 && computerResult == 0) {
        return Results.PLAYER_LOSE
    } else if (playerChoice == 0 && computerResult == 2) {
        return Results.PLAYER_WIN
    } else if (playerChoice == 0 && computerResult == 1) {
        return Results.PLAYER_LOSE
    }


    return Results.DEFAULT

}