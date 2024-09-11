package com.example.shoppingapp.UiLayer.Screens.Cart_Screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.R
import com.example.shoppingapp.UiLayer.Navigation.Routes
import com.example.shoppingapp.UiLayer.Screens.Shipping_Screen.ShippingViewModel
import com.example.shoppingapp.ui.theme.Pink80
import com.google.firebase.auth.FirebaseAuth

@Composable
fun CartScreen(
    firebaseAuth: FirebaseAuth,
    navController: NavHostController,
    ShippingVm: ShippingViewModel
) {
    val context = LocalContext.current
    val CartVM: CartViewModel = hiltViewModel()
    val userId =  firebaseAuth.uid.toString()
    LaunchedEffect(key1 = Unit) {
        CartVM.getProductCart(userId)
    }
    val cartItem =CartVM.cartItem.collectAsState()

    when(cartItem.value){
        is ReceiveCartState.Error -> {
            val errorMessage = (cartItem.value as ReceiveCartState.Error).message
            Toast.makeText(context, "$errorMessage", Toast.LENGTH_SHORT).show()
        }
        ReceiveCartState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = Pink80)
            }
        }
        is ReceiveCartState.Success -> {
            val cartList = (cartItem.value as ReceiveCartState.Success).cartList
            Box(modifier = Modifier.fillMaxSize()) {
                Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.ellipse_1_1_),
                        contentDescription = null
                    )
                }

                Column(modifier = Modifier
                    .padding(horizontal = 5.dp)
                    ) {
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
                            CartItemList(cartList,CartVM,userId)
                            Spacer(modifier = Modifier.padding(3.dp))
                            Box(
                                modifier = Modifier
                                    .height(0.4.dp)
                                    .fillMaxWidth()
                                    .background(Color.Black)
                            )
                        }
                        OutlinedButton(
                            onClick = {
                                // Checkout Handle
                                ShippingVm.PushData(cartList)
                                navController.navigate(Routes.Shipping(1))
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp),
                            shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(Color.Gray)
                        ) {
                            Text(text = "Check Out", color = Color.White)
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun CartItemList(cartList: List<CartModel>, CartVM: CartViewModel,uid:String) {
    val cart = remember {
        mutableStateOf(cartList)
    }
    LazyColumn (modifier = Modifier.fillMaxWidth()){
        items(cart.value){
            Row(horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp).fillMaxHeight(0.13f)
            ) {
                Row(modifier = Modifier.weight(2f)){
                    Card(
                        modifier = Modifier
                            .height(100.dp)
                            .width(60.dp)
                            .padding(horizontal = 5.dp),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        AsyncImage(
                           model =it.imageUrl,
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
                                .padding(vertical = 5.dp)
                                .width(100.dp), fontSize = 10.sp
                        )

                        Text(
                            text = it.size,
                            modifier = Modifier
                                .padding(vertical = 5.dp),
                            fontSize = 10.sp
                        )

                    }
                }

                    Text(
                        text ="${it.price}",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 10.dp)
                            .weight(1f)
                    )

                Text(
                    text = "${it.quantity}",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .weight(1f)
                )
                Text(
                    text ="${it.price}",
                    fontSize = 10.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .weight(1f)
                )

                Icon(imageVector = Icons.Default.Delete, contentDescription =null ,Modifier.fillMaxHeight().size(45.dp).padding(horizontal = 5.dp).clickable {
                    CartVM.removeToCart(uid,it.id)
                    CartVM.getProductCart(uid)
                })
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


@Composable
fun testUi(){
    val test = R.drawable.dress1
    Row(horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp).fillMaxHeight(0.13f)
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
                    painter = painterResource(id = test),
                    contentDescription = null,
                    Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                )
            }
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = "Random Name",
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .width(100.dp), fontSize = 10.sp
                )

                Text(
                    text = "M",
                    modifier = Modifier
                        .padding(vertical = 5.dp),
                    fontSize = 10.sp
                )

            }
        }

        Text(
            text ="4000",
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp)
                .weight(1f)
        )

        Text(
            text = "2",
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp)
                .weight(1f)
        )
        Text(
            text ="4000",
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp)
                .weight(1f)
        )


    }
}
