package com.example.shoppingapp.UiLayer.Screens.Wishlist_Screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.google.firebase.auth.FirebaseAuth

@Composable
//@Preview(showSystemUi = true)
fun WishlistScreen(firebaseAuth: FirebaseAuth, backClick: () -> Unit) {

    BackHandler {
        backClick()
    }
    //UserID
    val userId = firebaseAuth.uid.toString()
    //ViewModel
    val WISHLISTVM :WishlistViewModel = hiltViewModel()
    LaunchedEffect ( key1 = Unit ){
        WISHLISTVM.AllWishListItem(userId)
    }
    val WishListState = WISHLISTVM.CompleteWishList.collectAsState()
    val WishList = remember {
        mutableStateOf<List<CartModel>>(emptyList())
    }
    when(WishListState.value){
        is CompleteWishListState.Error -> {
        }
       is CompleteWishListState.Loading -> {
       }
        is CompleteWishListState.Success -> {
            val wishlist = (WishListState.value as CompleteWishListState.Success).wishlist
            WishList.value = wishlist
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 5.dp)
        ) {
        Column(modifier = Modifier
            .fillMaxSize()){
            Text(
                text = "Wishlist",
                color = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize = 25.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
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
                    text = "continue shopping",
                    color = Color.Gray,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontSize = 20.sp
                )
            }
            WishlistItemList(WishList)
        }
    }
}

@Composable
fun WishlistItemList(WishList: MutableState<List<CartModel>>) {

    val FontSize = 15.sp

    LazyColumn (modifier = Modifier.fillMaxWidth()){
        items(WishList.value){
            Row(horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Row(modifier = Modifier.weight(2f)){
                    Card(
                        modifier = Modifier
                            .height(150.dp)
                            .width(90.dp)
                            .padding(horizontal = 5.dp),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        AsyncImage(
                          model = it.imageUrl,
                            contentDescription = null,
                            Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(
                            text = it.name,
                            modifier = Modifier
                                .padding(vertical = 3.dp)
                                .width(100.dp), fontSize = FontSize
                        )
                        Text(
                            text = "Size : ${it.size}",
                            modifier = Modifier
                                .padding(vertical = 3.dp),
                            fontSize =FontSize
                        )
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Text(
                                text = it.id,
                                modifier = Modifier
                                    .padding(vertical = 5.dp),
                                fontSize = FontSize
                            )
                            Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(RectangleShape)
                                    .background(Color(android.graphics.Color.parseColor(it.color)))
                            )
                        }
                    }
                }

                Text(
                    text =" ${it.price}",
                    fontSize = FontSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 20.dp)
                        .weight(1f)
                )
            }

        }
    }
}