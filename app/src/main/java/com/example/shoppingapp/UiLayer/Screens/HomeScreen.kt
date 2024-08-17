package com.example.shoppingapp.UiLayer.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppingapp.R
import com.example.shoppingapp.ui.theme.Pink40
import com.example.shoppingapp.ui.theme.Pink80

@Composable
@Preview(showSystemUi = true)
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            SearchBar()
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OptionHead("Categories", "See More")
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Category()
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OptionHead(title = "Flash Sale", moreData = "See More")
            ShoppingList()
        }

    }
}

@Composable
fun OptionHead(title: String, moreData: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 20.sp
        )
        Text(
            text = moreData,
            color = Pink80,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 20.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchContent by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 6.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = searchContent,
            onValueChange = {
                searchContent = it
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            placeholder = { Text(text = "Search") },
            modifier = Modifier.fillMaxWidth(.90f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pink80,
                unfocusedBorderColor = Pink80
            ),
            shape = RoundedCornerShape(12.dp)
        )
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
    }
}

@Composable
fun Category() {

    val CategoryImg =
        listOf(R.drawable.frock, R.drawable.blouse, R.drawable.jeans, R.drawable.jumpsuit)
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(CategoryImg) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.Center
            ) { CategoryItem(icon = it) }
        }
    }
}

@Composable
fun CategoryItem(icon: Int) {

    Box(
        modifier = Modifier
            .padding(10.dp)
    ) {


        Image(
            painter = painterResource(id = icon),
            contentDescription = "Categories",
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .size(95.dp)
                .clip(CircleShape)
                .border(1.dp, Color.LightGray, CircleShape)
        )


    }
}

data class ShoppingDescription(
    val dressImg: Int,
    val ProductName: String,
    val ProductModel: String,
    val ProductPrice: String,
    val ProductDiscountedPrice: String,
    val ProductDiscountPercentage: String,
)

@Composable
fun ShoppingList() {
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
            R.drawable.dress3, "One Shoulder Linen Dress",
            "GF1025",
            "Rs: 5740",
            "Rs: 7180",
            "20% off"
        ),
        ShoppingDescription(
            R.drawable.dress4,
            "Puff Sleeve Dress",
            "GF1047",
            "Rs: 5270",
            "Rs: 6200",
            "15% off"
        )
    )
    LazyRow {
        items(dressDescription) {
            DressImage(it)
            Box(modifier = Modifier.padding(vertical = 20.dp))
        }
    }
}



@Composable
fun DressImage(dress: ShoppingDescription) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .height(250.dp)
                .width(150.dp)
                .padding(vertical = 15.dp, horizontal = 5.dp), shape = RoundedCornerShape(25.dp)
        ) {
            Image(
                painter = painterResource(id = dress.dressImg),
                contentDescription = null,
                Modifier.fillMaxSize(), contentScale = ContentScale.Crop
            )
        }
        Card(
            modifier = Modifier
                .height(200.dp)
                .width(150.dp)
                .padding(vertical = 15.dp, horizontal = 5.dp), colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(13.dp),
            border = BorderStroke(0.75.dp, Color.Black)
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)) {
                Text(
                    text = dress.ProductName,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    fontSize = 18.sp, textAlign = TextAlign.Center
                )
                Text(
                    text = dress.ProductModel,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                    fontSize = 15.sp
                )
            Text(text = dress.ProductPrice,modifier = Modifier.padding( horizontal = 5.dp),fontSize = 13.sp)
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = dress.ProductDiscountedPrice, textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                        fontSize = 15.sp
                    )
                    Text(text = dress.ProductDiscountPercentage, fontSize = 15.sp, color = Pink80
                    )
                }
            }
        }
    }
}
