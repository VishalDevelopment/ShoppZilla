package com.example.shoppingapp.UiLayer.Screens

import android.text.Layout
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R

@Composable
//@Preview(showSystemUi = true)
fun CartScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ellipse_1_1_),
                contentDescription = null
            )
        }

        Column(modifier = Modifier
            .padding(horizontal = 5.dp)
            .padding(top = 50.dp)) {
            Text(
                text = "Shopping Cart",
                color = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize = 25.sp
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
                    text = "continue shopping",
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            CartItemHead()
           Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween){
               Column(Modifier.fillMaxHeight(.90f)){
                   CartItemList()
                   Spacer(modifier = Modifier.padding(3.dp))
                   Box(
                       modifier = Modifier
                           .height(0.4.dp)
                           .fillMaxWidth()
                           .background(Color.Black)
                   )
               }
               OutlinedButton(
                   onClick = { },
                   modifier = Modifier.fillMaxWidth(),
                   shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(Color.Gray)
               ) {
                   Text(text = "Check Out", color = Color.White)
               }
            }
        }

    }
}

@Composable
fun CartItemList() {
    val dressDescription = listOf(
        ShoppingDescription(
            R.drawable.dress1,
            "One Shoulder Linen Dress",
            "GF1025",
            "Rs: 5740",
            "Rs: 7180",
            "20% off"
        ),
        ShoppingDescription(
            R.drawable.dress2,
            "Puff Sleeve Dress",
            "GF1047",
            "Rs: 5270",
            "Rs: 6200",
            "15% off"
        ),
        ShoppingDescription(
            R.drawable.dress1,
            "One Shoulder Linen Dress",
            "GF1025",
            "Rs: 5740",
            "Rs: 7180",
            "20% off"
        )
    )
    LazyColumn (modifier = Modifier.fillMaxWidth()){
        items(dressDescription){
            Row(horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Row(modifier = Modifier.weight(2f)){
                    Card(
                        modifier = Modifier
                            .height(100.dp)
                            .width(60.dp)
                            .padding(horizontal = 5.dp),
                        shape = RoundedCornerShape(18.dp)
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
                                .padding(vertical = 5.dp)
                                .width(100.dp), fontSize = 10.sp
                        )

                        Text(
                            text = it.ProductModel,
                            modifier = Modifier
                                .padding(vertical = 5.dp),
                            fontSize = 10.sp
                        )

                    }
                }

                    Text(
                        text = it.ProductPrice,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 10.dp)
                            .weight(1f)
                    )

                Text(
                    text = "1",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .weight(1f)
                )
                Text(
                    text = it.ProductPrice,
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .weight(1f)
                )
            }

        }
    }
}

@Composable
fun CartItemHead(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "Items",
            color = Color.Gray,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp, modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp)
        )


        Text(
            text = "Price",
            color = Color.Gray,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        )
        Text(
            text = "QTY",
            color = Color.Gray,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )

        Text(
            text = "Total",
            color = Color.Gray,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp, modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )}
}

