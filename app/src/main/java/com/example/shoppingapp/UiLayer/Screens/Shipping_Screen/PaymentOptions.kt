package com.example.shoppingapp.UiLayer.Screens.Shipping_Screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppingapp.ui.theme.Pink80

@Composable

fun PaymentOptions(paymentOption: (flag :Int) -> Unit) {

    val paymentOption = listOf("Cash On Delivery", "Pay Now")
    var selectedOption by remember {
        mutableStateOf("Pay Now")
    }
    LaunchedEffect(key1 = selectedOption) {
        if (selectedOption=="Pay Now"){
            paymentOption(1)
        }
        if (selectedOption == "Cash On Delivery"){
            paymentOption(0)
        }
    }

        Card(
            border = BorderStroke(1.dp, Pink80),
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp),
            shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            paymentOption.forEach { option ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(horizontal = 15.dp, vertical = 3.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,modifier= Modifier
                            .fillMaxSize()
                            .clickable {
                                selectedOption = option
                            }){
                        RadioButton(selected = selectedOption == option, onClick = null, colors = RadioButtonDefaults.colors(Pink80))
                        Text(
                            text = "$option",
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

    }
}