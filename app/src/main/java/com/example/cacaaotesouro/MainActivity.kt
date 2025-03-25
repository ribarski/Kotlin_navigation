package com.example.cacaaotesouro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.time.LocalTime


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val controladorNavegacao = rememberNavController()
            val tempoInicio = remember { LocalTime.now() }

            NavHost(
                navController = controladorNavegacao,
                startDestination = "inicio"
            ) {
                // Tela Inicial
                composable("inicio") {
                    TelaInicial(controladorNavegacao)
                }

                // Telas de Pista
                composable("pista1") {
                    TelaPista(
                        pista = "Em que lugar você guarda seus segredos?",
                        respostaCorreta = "diario",
                        controladorNavegacao = controladorNavegacao,
                        rotaProxima = "pista2"
                    )
                }

                composable("pista2") {
                    TelaPista(
                        pista = "Que objeto reflete tudo ao seu redor?",
                        respostaCorreta = "espelho",
                        controladorNavegacao = controladorNavegacao,
                        rotaProxima = "pista3"
                    )
                }

                composable("pista3") {
                    TelaPista(
                        pista = "Que lugar tem muitos livros e silêncio?",
                        respostaCorreta = "biblioteca",
                        controladorNavegacao = controladorNavegacao,
                        rotaProxima = "tesouro"
                    )
                }

                // Tela do Tesouro
                composable("tesouro") {
                    TelaTesouro(
                        controladorNavegacao = controladorNavegacao,
                        tempoInicio = tempoInicio
                    )
                }
            }
        }
    }
}

@Composable
fun TelaInicial(controladorNavegacao: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Caça ao Tesouro",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { controladorNavegacao.navigate("pista1") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Caça ao Tesouro")
        }
    }
}

@Composable
fun TelaPista(
    pista: String,
    respostaCorreta: String,
    controladorNavegacao: NavController,
    rotaProxima: String
) {
    var resposta by remember { mutableStateOf(TextFieldValue("")) }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = pista,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        TextField(
            value = resposta,
            onValueChange = { resposta = it },
            label = { Text("Sua resposta") },
            modifier = Modifier.fillMaxWidth()
        )

        mensagemErro?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { controladorNavegacao.navigateUp() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Voltar")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    if (resposta.text.lowercase().trim() == respostaCorreta) {
                        controladorNavegacao.navigate(rotaProxima) {
                            popUpTo(rotaProxima) { inclusive = true }
                        }
                    } else {
                        mensagemErro = "Resposta incorreta. Tente novamente!"
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Próxima Pista")
            }
        }
    }
}

@Composable
fun TelaTesouro(
    controladorNavegacao: NavController,
    tempoInicio: LocalTime
) {
    val tempoTotal = java.time.Duration.between(tempoInicio, LocalTime.now())
    val minutos = tempoTotal.toMinutes()
    val segundos = tempoTotal.seconds % 60

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Parabéns! Você encontrou o tesouro!",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Tempo total: $minutos min $segundos seg",
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                controladorNavegacao.navigate("inicio") {
                    popUpTo("inicio") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Recomeçar")
        }
    }
}