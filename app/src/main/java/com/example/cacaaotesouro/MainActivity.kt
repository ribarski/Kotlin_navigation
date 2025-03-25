package com.example.cacaaotesouro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "/tela02"
            ){
                composable("/home"){
                    Tela(
                        "Home",
                        clickB1 = { navController.navigate("/tela02") },
                        clickB2 = { navController.navigate("/tela03") },
                    )
                }
                composable("/tela02"){
                    Tela(
                        "Tela02",
                        clickB1 = { navController.navigate("/home") },
                        clickB2 = { navController.navigate("/tela03") }
                    )
                }
                composable("/tela03"){
                    Tela(
                        "Tela03",
                        clickB1 = {  navController.navigate("/tela02") },
                        clickB2 = { navController.navigate("/home") }
                    )
                }
            }
        }
    }
}

@Composable
fun Tela(name: String,
         clickB1: () -> Unit,
         clickB2: () -> Unit,
         ){
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = name, fontSize = 36.sp)
        Button(onClick = clickB1) {
            Text(text = "B1", fontSize = 36.sp)
        }
        Button(onClick = clickB2) {
            Text(text = "B2", fontSize = 36.sp)
        }
    }
}