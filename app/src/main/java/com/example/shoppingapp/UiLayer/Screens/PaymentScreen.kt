package com.example.shoppingapp.UiLayer.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.ui.theme.Pink80

@Composable
@Preview(showBackground = true)
fun PaymentScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Shipping",
                color = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize = 25.sp, modifier = Modifier.padding(top = 50.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        Color.Gray
                    ), alignment = Alignment.CenterStart, modifier = Modifier.fillMaxHeight()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Return to Shipping",
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontSize = 20.sp
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            )
            {
                ShoppingMethod()
            }
        }
    }
}

@Composable
fun ShoppingMethod() {
    val radioOptions = listOf("Sampath Bank IPG", "Cash On Delivery (COD)")
    val selectedOption = remember {
        mutableStateOf(radioOptions[0])
    }

    Card(modifier = Modifier.height(150.dp), border = BorderStroke(1.dp,Pink80), colors = CardDefaults.cardColors(Color.White)){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center){
            radioOptions.forEach { text ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedOption.value == text,
                        onClick = { selectedOption.value = text }
                    ), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = selectedOption.value == text, onClick = {
                        selectedOption.value = text
                    })
                    Text(text = text)
                }
            }
        }
    }
}