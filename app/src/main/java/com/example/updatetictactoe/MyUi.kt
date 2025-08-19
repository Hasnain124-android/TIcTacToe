package com.example.updatetictactoe // Package declaration for the app

// Import statements for required classes and functions
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// List of all winning combinations in TicTacToe (rows, columns, diagonals)
val mainList = listOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(0, 3, 6),
    listOf(1, 4, 7),
    listOf(2, 5, 8),
    listOf(0, 4, 8),
    listOf(2, 4, 6),
)

// Lists to store the moves of the "X" and "O" players
val cross = mutableListOf<Int>()
val circle = mutableListOf<Int>()

// Counter for number of moves made
var count = 0

// Resource IDs for icons used in the UI
var restartIcon = R.drawable.restart
var dismissIcon = R.drawable.cross

@Composable
fun DesignGame(navController: NavController) { // Main game UI
    BackHandler {
        // Prevents going back from the game screen with the device back button
    }

    // The board state with 9 cells initialized as empty strings
    val board = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
    var gameOver by remember { mutableStateOf(false) } // Tracks if the current round is over
    var showDialog by remember { mutableStateOf(false) } // Controls showing the winner dialog
    var winner by remember { mutableStateOf("") } // Stores the final match winner message
    var currentTurn by remember { mutableStateOf(listOf("X", "O").random()) }
    var crosspoints by remember { mutableIntStateOf(0) } // X player score
    var circlepoints by remember { mutableIntStateOf(0) } // O player score
    var drawpoints by remember { mutableIntStateOf(0) } // Draw score

    // A list to display current scores
    val pointsList = listOf(
        "Cross: $crosspoints",
        "Draw: $drawpoints",
        "Circle: $circlepoints"
    )

    var rounds by remember { mutableIntStateOf(1) } // Round number tracker
    var roundEnded by remember { mutableStateOf(false) } // Tracks if current round is finished
    val context = LocalContext.current // Context for showing Toasts

    // Lambda function to reset the entire game state
    val gamestate = {
        cross.clear()
        circle.clear()
        for (i in board.indices) { // Reset board cells
            board[i] = ""
        }
        rounds = 1
        count = 0
        roundEnded = false
        gameOver = false
        circlepoints = 0
        crosspoints = 0
        drawpoints = 0
        showDialog = false
    }

    // Lambda function to handle draw scenario
    val drawstate = {
        drawpoints++
        gameOver = !gameOver
        Toast.makeText(context, "ROUND TIED", Toast.LENGTH_SHORT).show()
        roundEnded = true
    }

    // Effect that runs when `roundEnded` changes
    LaunchedEffect(roundEnded) {
        if (roundEnded) {
            if (rounds == 3) { // If final round
                showDialog = true
                winner = if (circlepoints > 1) {
                    "CIRCLE WINS THE MATCH"
                } else if (crosspoints > 1) {
                    "CROSS WINS THE MATCH"
                } else {
                    "GAME IS TIED"
                }
            } else { // If not last round
                cross.clear()
                circle.clear()
                delay(2000) // Wait 2 seconds before next round
                for (i in board.indices) { // Reset board
                    board[i] = ""
                }
                count = 0
                gameOver = !gameOver
                rounds++
                roundEnded = false
            }
        }
    }

    // If game over, show winner dialog
    if (showDialog) {
        MyDialog(
            title = "WINNER",
            message = "$winner \nWANNA PLAY AGAIN",
            confirmButtonIcon = restartIcon,
            dismissButtonIcon = dismissIcon,
            onDismiss = {
                navController.navigate("Home")
                gamestate()
            },
            onConfirm = {
                gamestate()
                currentTurn = "X"
            }
        )
    }
    
    // Background and main layout
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF531B5E), // Purple
                        Color(0xFF3D163F)  // Dark purple
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Game title
            Text(
                text = "TicTacToe",
                fontSize = 70.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFFF31000), // Red
                            Color(0xFFFFA500)  // Orange
                        )
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().background(Color.Transparent),
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.gamename))
            )

            // Round number display
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ROUND --> $rounds",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFFF31000),
                                Color(0xFFFFA500)
                            )
                        )
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().background(Color.Transparent).padding(16.dp),
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily(Font(R.font.content))
                )

                Spacer(
                    modifier = Modifier.fillMaxWidth().height(2.dp).background(Color.Transparent)
                )

                // Current turn display
                Text(
                    text = "Current Turn --> $currentTurn",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFFF31000),
                                Color(0xFFFFA500)
                            )
                        )
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().background(Color.Transparent).padding(16.dp),
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily(Font(R.font.content))
                )
            }

            // Main game board
            Box(modifier = Modifier.wrapContentSize()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Score display row
                    Box(
                        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            repeat(pointsList.size) { index ->
                                Text(
                                    text = pointsList[index],
                                    modifier = Modifier.padding(8.dp),
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.fillMaxWidth().height(5.dp))

                    // 3x3 grid
                    for (row in 0 until 3) {
                        Row {
                            for (col in 0 until 3) {
                                val index = row * 3 + col
                                Card(
                                    modifier = Modifier
                                        .size(106.dp)
                                        .padding(3.dp)
                                        .clickable(enabled = !gameOver) {
                                            count++
                                            if (board[index] == "") {
                                                board[index] = currentTurn
                                                if (currentTurn == "X") {
                                                    cross.add(index)
                                                    currentTurn = "O"
                                                    if (check() == 1) {
                                                        crosspoints++
                                                        gameOver = !gameOver
                                                        Toast.makeText(context, "CROSS WIN THE ROUND", Toast.LENGTH_SHORT).show()
                                                        roundEnded = true
                                                    } else if (check() == 3) {
                                                        drawstate()
                                                    }
                                                } else if (currentTurn == "O") {
                                                    circle.add(index)
                                                    currentTurn = "X"
                                                    if (check() == 2) {
                                                        circlepoints++
                                                        gameOver = !gameOver
                                                        Toast.makeText(context, "CIRCLE WIN THE ROUND", Toast.LENGTH_SHORT).show()
                                                        roundEnded = true
                                                    } else if (check() == 3) {
                                                        drawstate()
                                                    }
                                                }
                                            }
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color.White),
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        when (board[index]) {
                                            "X" -> CrossDesign()
                                            "O" -> CircleDesign()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Control buttons row
            Row(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Leave button
                Button(
                    onClick = {
                        navController.navigate("Home")
                        gamestate()
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFC20000),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(150.dp),
                ) {
                    Text(text = "LEAVE", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                }

                // Play again button
                Button(
                    onClick = {
                        gamestate()
                        currentTurn = "X"
                    },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4AC1F3),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(150.dp),
                ) {
                    Text(text = "AGAIN", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                }
            }
        }
    }
}

// Composable for drawing the X symbol
@Composable
fun CrossDesign() {
    Box(
        modifier = Modifier
            .border(width = 2.dp, color = Color(0xFFEFA746), shape = RoundedCornerShape(12.dp))
            .size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(dismissIcon),
            contentDescription = "Icon",
            modifier = Modifier.size(60.dp)
        )
    }
}

// Composable for drawing the O symbol
@Composable
fun CircleDesign() {
    Box(
        modifier = Modifier
            .border(width = 2.dp, color = Color(0xFF11B2A7), shape = RoundedCornerShape(12.dp))
            .size(80.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.circle),
            contentDescription = "Icon",
            modifier = Modifier.size(60.dp)
        )
    }
}

// Function to check the game state
fun check(): Int {
    for (combo in mainList) {
        if (cross.containsAll(combo)) { // X wins
            return 1
        } else if (circle.containsAll(combo)) { // O wins
            return 2
        } else if (count == 9) { // Draw
            return 3
        }
    }
    return -1 // No winner yet
}
