package com.example.shoppingapp.UiLayer.Screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.ui.theme.Pink80

@Composable
@Preview(showBackground = true , heightDp = 1000, widthDp = 500)
fun ShippingScreen (){

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
            Row (modifier = Modifier.height(40.dp), verticalAlignment = Alignment.CenterVertically){
                Image(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        Color.Gray
                    ), alignment = Alignment.CenterStart, modifier = Modifier.fillMaxHeight()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "return to cart",
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontSize = 20.sp
                )
            }
                ProductList()
            Billing()
            PersoanlInformation()
        }
    }
}

@Composable
fun ProductList() {
    val dressDescription = listOf(
        ShoppingDescription(
            R.drawable.dress1,
            "One Shoulder Linen Dress",
            "GF1025",
            "Rs: 5740",
            "Rs: 7180",
            "20% off"
        )
    )

    LazyColumn(modifier =  Modifier.fillMaxWidth()) {
        items(dressDescription){
           Column(modifier =Modifier.fillMaxWidth()) {
                Row(modifier =Modifier.fillMaxWidth()){
                    Card(
                        modifier = Modifier
                            .height(120.dp)
                            .width(65.dp)
                            .padding(horizontal = 5.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = it.dressImg),
                            contentDescription = null,
                            Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(
                            text = it.ProductName,
                            modifier = Modifier
                                .padding(vertical = 5.dp) ,fontSize = 15.sp
                        )

                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = it.ProductModel,
                                modifier = Modifier
                                    .padding(vertical = 5.dp),
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(RectangleShape)
                                    .background(Color.Red)
                            )
                        }
                    }
                    Text(text = it.ProductPrice,
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp), textAlign = TextAlign.End)
                }

            }
        }
    }



}
@Composable
fun Billing(){

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp)){
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(0.90.dp)
            .background(Color.Black)
            .padding(top = 3.dp))
        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(vertical = 3.dp)
            .fillMaxWidth()){
            Text(text = "Sub Total")
            Text(text = "Rs 5740")
        }
        Row (horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier
            .padding(vertical = 3.dp)
            .fillMaxWidth()){
            Text(text = "Shipping")
            Text(text = "Free")
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(0.90.dp)
            .background(Color.Black)
            .padding(top = 3.dp))

        Row (horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier
            .padding(vertical = 5.dp)
            .fillMaxWidth()){
            Text(text = "Total")
            Text(text = "Rs 5740")
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(1.5.dp)
            .background(Color.Black)
            .padding(top = 3.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersoanlInformation(){
    var email by remember {
        mutableStateOf("")
    }
    var fname by remember {
        mutableStateOf("")
    }
    var lname by remember {
        mutableStateOf("")
    }
    var ischeck by remember {
        mutableStateOf(false)
    }
    Column(Modifier.fillMaxSize()) {
        Column(){
            Text(text = "Contact Information")
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(12.dp))
        }
        Column{
            Text(text = "Shipping Address")
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = { Text(text = "Country/Region") },
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(12.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
,            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(value = fname,
                onValueChange = {
                    fname = it
                },
                placeholder = { Text(text = "First Name") },
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(18.dp)
            )
            OutlinedTextField(value = lname,
                onValueChange = {
                    lname = it
                },
                placeholder = { Text(text = "Last Name") },
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(18.dp)
            )
        }

        OutlinedTextField(value = email,
            onValueChange = {
                email = it
            },
            placeholder = { Text(text = "Adress") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pink80,
                unfocusedBorderColor = Pink80
            ),
            shape = RoundedCornerShape(18.dp)
        )


        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(value = fname,
                onValueChange = {
                    fname = it
                },
                placeholder = { Text(text = "City") },
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(18.dp)
            )
            OutlinedTextField(value = lname,
                onValueChange = {
                    lname = it
                },
                placeholder = { Text(text = "Postal code") },
                modifier = Modifier
                    .width(150.dp)
                    .padding(horizontal = 8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(18.dp)
            )
        }


        OutlinedTextField(value = email,
            onValueChange = {
                email = it
            },
            placeholder = {
                Text(text = "Contact number")
                          },
            modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pink80,
                unfocusedBorderColor = Pink80
            ),
            shape = RoundedCornerShape(18.dp)
        )


        Row(modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = ischeck, onCheckedChange = {
                ischeck = !ischeck
            })
            Text(text = "Save this information for next time")
        }


        OutlinedButton(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(text = "Continue to shopping", color = Color.White)
        }

    }
}
