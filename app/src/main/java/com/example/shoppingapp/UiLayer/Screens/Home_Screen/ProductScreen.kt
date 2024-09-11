package com.example.shoppingapp.UiLayer.Screens.Home_Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.Model.TestModel
import com.example.shoppingapp.UiLayer.Navigation.Routes
import com.example.shoppingapp.UiLayer.Screens.Cart_Screen.CartViewModel
import com.example.shoppingapp.UiLayer.Screens.Shipping_Screen.ShippingViewModel
import com.example.shoppingapp.UiLayer.Screens.Wishlist_Screen.WishListReceiveState
import com.example.shoppingapp.UiLayer.Screens.Wishlist_Screen.WishlistViewModel
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import com.example.shoppingapp.UiLayer.ViewModel.SpecificProduct
import com.example.shoppingapp.ui.theme.Pink80
import com.example.shoppingapp.ui.theme.PinkDark
import com.google.firebase.auth.FirebaseAuth

val sizeSelectedImage = mutableStateOf("")
val colorSelected = mutableStateOf("")
val addToCartClicked = mutableStateOf(0)
val quantity = mutableStateOf(0)

@Composable
fun ProductScreen(
    productId: String,
    firebaseAuth: FirebaseAuth,
    navController: NavHostController,
    ShippingVm: ShippingViewModel
) {
    val context = LocalContext.current
    //ViewModel
    val CartVM: CartViewModel = hiltViewModel()
    val WishListVM: WishlistViewModel = hiltViewModel()
    val viewmodel: ShoppingVm = hiltViewModel()

    val userId = firebaseAuth.uid.toString()
    val productData = remember {
        mutableStateOf<TestModel>(TestModel())
    }

    val checkWishlist = remember {
        mutableStateOf(false)
    }
    val isWishlisted = WishListVM.isWishlistAvailable.collectAsState()
    when (isWishlisted.value) {
        is WishListReceiveState.Error -> {
            Log.d("ISWISHLISTED", "ERR : ")
        }

        WishListReceiveState.Loading -> {
            Log.d("ISWISHLISTED", "LOAD : ")

        }

        is WishListReceiveState.Success -> {
            val isWishlist = (isWishlisted.value as WishListReceiveState.Success).isAvailable
            checkWishlist.value = isWishlist
        }
    }
    LaunchedEffect(Unit) {
        viewmodel.getSpecificProduct(productId)
        WishListVM.gettoWishlist(userId, productId)
    }

    val state = viewmodel.productdata.collectAsState()
    when (state.value) {
        is SpecificProduct.Error -> {

        }

        SpecificProduct.Loading -> {
        }

        is SpecificProduct.Success -> {
            val product = (state.value as SpecificProduct.Success).product
            productData.value = product
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Card(
                modifier = Modifier
                    .fillMaxHeight(.60f)
                    .fillMaxWidth(.7f)
                    .padding(vertical = 10.dp)
            ) {

                val productImage = productData.value?.imageUrl
                AsyncImage(
                    model = productImage,
                    contentDescription = "Product Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 10.dp, 10.dp, 0.dp)
            ) {
                Text(
                    text = "${productData.value?.name}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif
                )
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily.SansSerif
                                )
                            ) {
                                append("Rs ")
                            }
                            withStyle(style = SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                                append(
                                    "${productData.value?.actualPrice}"
                                )
                            }
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = "${productData.value?.discountedPrice}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                    Text(
                        text = "-${productData.value?.discountedPercentage}%",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.SansSerif,
                        modifier = Modifier.padding(horizontal = 5.dp),
                        color = PinkDark
                    )

                }
                Spacer(modifier = Modifier.height(5.dp))

                ProductColor(productData.value.color)
                Spacer(modifier = Modifier.height(5.dp))
                ProductSize(productData.value.Size)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .height(45.dp)
                        .border(1.dp, Color.Black), contentAlignment = Alignment.Center
                ) {
                    Row() {

                        Image(imageVector = Icons.Default.Remove,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxHeight()
                                .clickable {
                                    if (quantity.value > 0) {
                                        quantity.value = (quantity.value.toInt() - 1)
                                    }
                                })
                        OutlinedTextField(
                            readOnly = true, value = quantity.value.toString(), onValueChange = {
                                quantity.value = it.toInt()
                            }, modifier = Modifier
                                .width(50.dp)
                                .height(45.dp)
                        )
                        Image(imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxHeight()
                                .clickable {
                                    if (quantity.value < 10) {
                                        quantity.value = (quantity.value.toInt() + 1)
                                    }
                                })

                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Description",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontFamily = FontFamily.SansSerif
                )

                val des = productData.value?.description
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "$des",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontFamily = FontFamily.SansSerif
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        // Cart Code And Move to Cart Screen
                        if (colorSelected.value != "" && sizeSelectedImage.value != "") {
                            val productInfo = mutableListOf<CartModel>()
                            productInfo.add(CartModel(productData.value.id,productData.value.name,productData.value.imageUrl,productData.value.discountedPrice,
                                quantity.value , colorSelected.value , sizeSelectedImage.value))
                            ShippingVm.PushData(productInfo)
                            navController.navigate(Routes.Shipping(0))
                        } else {
                            Toast.makeText(
                                context,
                                "Please Select quantity ,color And Size",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp), colors = ButtonDefaults.buttonColors(Pink80)
                ) {
                    Text(text = "Buy Now", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(
                    onClick = {
                        //  Cart Code
                        if (colorSelected.value != "" && sizeSelectedImage.value != "") {
                            CartVM.addtoCart(
                                userId.toString(),
                                CartModel(
                                    productData.value.id,
                                    productData.value.name,
                                    productData.value.imageUrl,
                                    productData.value.discountedPrice,
                                    quantity.value,
                                    colorSelected.value,
                                    sizeSelectedImage.value
                                )
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Please Select quantity ,color And Size",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray)
                ) {
                    Text(text = "Add to Cart", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            if (checkWishlist.value == true) {
                                WishListVM.removetoWishlist(userId, productId)
                                WishListVM.gettoWishlist(userId, productId)

                            } else {
                                WishListVM.addtoWishList(
                                    userId, CartModel(
                                        productData.value.id,
                                        productData.value.name,
                                        productData.value.imageUrl,
                                        productData.value.discountedPrice,
                                        quantity.value,
                                        colorSelected.value,
                                        sizeSelectedImage.value
                                    )
                                )
                                WishListVM.gettoWishlist(userId, productId)


                            }

                        }, horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (checkWishlist.value == true) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = null, tint = Color.Black
                    )
                    Box(modifier = Modifier.width(3.dp))
                    Text(text = "Add to Wishlist")
                }
            }

        }
    }
}

fun isValidHexCode(hexCode: String): Boolean {
    val regex = Regex("^#[0-9a-fA-F]{6}$")
    return regex.matches(hexCode)
}

@Composable
fun ProductColor(hexCodes: List<String>) {
    val validHexCodes = hexCodes.filter { isValidHexCode(it) }
    LazyRow {
        items(hexCodes) {
            Card(
                modifier = Modifier
                    .size(30.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RectangleShape)
                        .background(Color(android.graphics.Color.parseColor(it)))
                        .clickable {
                            colorSelected.value = it
                        }
                )
            }
            Box(modifier = Modifier.padding(horizontal = 3.dp))
        }
    }
}

@Composable
fun ProductSize(productSize: List<String>) {

    LazyRow() {
        items(productSize) {
            Card(
                modifier = Modifier
                    .size(30.dp), border = BorderStroke(1.dp, Color.Black)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RectangleShape)
                        .clickable {
                            sizeSelectedImage.value = it
                        }, contentAlignment = Alignment.Center
                ) {
                    Text(text = " $it")
                }
            }
            Box(modifier = Modifier.padding(horizontal = 3.dp))
        }
    }
}

