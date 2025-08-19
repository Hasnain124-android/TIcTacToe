package com.example.updatetictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.updatetictactoe.ui.theme.UpdateTicTacToeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        setContent {
            UpdateTicTacToeTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background ) {
                    val navController = rememberNavController()
                    NavHost (navController = navController, startDestination = "Splash"){
                        composable ("Splash") {
                            SplashScreen(navController = navController)
                        }
                            composable("Home")
                            { MyHome(navController = navController) }
                        composable("Game"){
                            DesignGame(navController = navController)
                        }

                    }
                    }
                }
            }
        }
    }
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UpdateTicTacToeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background ) {
            val navController = rememberNavController()
            NavHost (navController = navController, startDestination = "Splash"){
                composable ("Splash") {
                    SplashScreen(navController = navController)
                }
                composable("Home")
                { MyHome(navController = navController) }
                }
            }
        }
    }

