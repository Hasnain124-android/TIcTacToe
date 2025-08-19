package com.example.updatetictactoe



import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


var closingIcon = R.drawable.forclosingapp
var cancel = R.drawable.cancel

@Composable
fun MyHome(navController: NavController){

    val activity = LocalActivity.current
    var showDialog by remember { mutableStateOf(false) }

    BackHandler {
        // Instead of exiting directly, show the dialog
        showDialog = true
    }
    if (showDialog) {
        MyDialog(
            title = "Exit App",
            message ="Are you sure you want to exit?",
            confirmButtonIcon = closingIcon,
            dismissButtonIcon = cancel,
            onDismiss = { showDialog = false },  // Close dialog on Cancel
            onConfirm = { activity?.finish() },
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF803091), // Purple
                        Color(0xFF3D163F)  // Orange
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "WELCOME",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xFFF31000), // Purple
                            Color(0xFFFFA500)  // Orange
                        )
                    )
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Transparent
                    )
                    .padding(16.dp),
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily(Font(R.font.gamename))
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
            )

            Box(
                modifier = Modifier
                    .size(300.dp)  // width & height 300.dp
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp) // rounded corners with 16.dp radius
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Button(

                        onClick = {navController.navigate("Game")},
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF6E46),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .width(240.dp),
                    ) {
                        Text(
                            text = "START",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }


                    Button(
                        onClick = {
                            showDialog = true },
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF0000),
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .width(240.dp),
                    ) {
                        Text(
                            text = "EXIT",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )
                    }

                }

            }
        }
    }
}
