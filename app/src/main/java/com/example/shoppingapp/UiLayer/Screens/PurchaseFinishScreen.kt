package com.example.shoppingapp.UiLayer.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shoppingapp.R
import com.example.shoppingapp.ui.theme.Pink80

@Composable

fun PurchaseFinishScreen(navController: NavHostController, popAllBackScreen: () -> Unit) {

    BackHandler {
        popAllBackScreen()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(id = R.drawable.success_icon),
                contentDescription = null,
                Modifier
                    .padding(vertical = 8.dp)
                    .size(160.dp)
            )
            Text(text = "Successful Purchase!", fontSize = 20.sp)
            OutlinedButton(
                onClick = {
                   popAllBackScreen()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(Pink80)
            ) {
                Text(text = "Continue Shopping", color = Color.White)
            }
        }
    }
}