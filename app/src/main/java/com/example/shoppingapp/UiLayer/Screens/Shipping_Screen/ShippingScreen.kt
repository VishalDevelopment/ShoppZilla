package com.example.shoppingapp.UiLayer.Screens.Shipping_Screen


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.shoppingapp.DomainLayer.Model.AddressModel
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.Model.OrderForm
import com.example.shoppingapp.UiLayer.Screens.Cart_Screen.CartViewModel
import com.example.shoppingapp.ui.theme.Pink80
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ShippingScreen(flag: Int, ShippingVm: ShippingViewModel, firebaseAuth: FirebaseAuth) {

    val uid = firebaseAuth.uid.toString()
    val CartVM: CartViewModel = hiltViewModel()
    val ShippingProduct = ShippingVm.shippingList
    LaunchedEffect(key1 = Unit) {
        ShippingVm.GetAddress(uid)
    }

    val Address = remember {
        mutableStateOf<AddressModel>(AddressModel())
    }

    val addressFromCloud = ShippingVm.AddressData.collectAsState()
    if (flag == 1) {
        Log.d("FLAG", "CART :   $ShippingProduct")
    }
    if (flag == 0) {
        Log.d("FLAG", "PROD : $ShippingProduct")
    }

    when (addressFromCloud.value) {
        is AddressState.Error -> {
//           val error = addressFromCloud.value.message
        }

        is AddressState.Loading -> {
            Log.d("SHIPPINGDONE", "LOAD : ")
        }

        is AddressState.Success -> {
            val address = (addressFromCloud.value as AddressState.Success).address
            Address.value = address
            Log.d("SHIPPINGDONE", "$address")
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Shipping",
                color = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize = 25.sp, modifier = Modifier.padding(top = 50.dp)

            )
            val PaymentFlag = remember { mutableStateOf(0) }

            val SaveAddress = remember {
                mutableStateOf(false)
            }
            Spacer(modifier = Modifier.height(10.dp))
            ProductList(ShippingProduct.value)
            Column {
                Billing()
                PersoanlInformation(Address.value) { address, isCheck ->
                    Address.value = address
                    SaveAddress.value = isCheck

                    Log.d("IMPDATA", "it : SAVEADDRESS : $isCheck")
                }
                PaymentOptions() {
                    PaymentFlag.value = it
                    Log.d("PAYMENTMETHOD", "it : $it")
                }
                OutlinedButton(
                    onClick = {
                        if (PaymentFlag.value == 0) {
                            Log.d("PAYMENTMETHOD", "COD")
                            if (SaveAddress.value == true) {
                                Log.d("PAYMENTMETHOD", "it : ADDRESS SAVE")
                                // Save Address Over FireStore & Then Place Order
                                ShippingVm.Addaddress(uid, Address.value)
                                ShippingProduct.value.forEach {
                                    val orderForm = OrderForm(
                                        uid,
                                        CartModel(
                                            it.id,
                                            it.name,
                                            it.imageUrl,
                                            it.price,
                                            it.quantity,
                                            it.color,
                                            it.size
                                        ),
                                        Address.value
                                    )
                                    ShippingVm.placeOrder(orderForm)
                                }
                            } else {
                                //  Then Place Order Only
                                ShippingProduct.value.forEach {
                                    val orderForm = OrderForm(
                                        uid,
                                        CartModel(
                                            it.id,
                                            it.name,
                                            it.imageUrl,
                                            it.price,
                                            it.quantity,
                                            it.color,
                                            it.size
                                        ),
                                        Address.value
                                    )
                                    ShippingVm.placeOrder(orderForm)
                                }
                            }

                        }
                        if (PaymentFlag.value == 1) {
                            ShippingProduct.value.forEach {
                                val orderForm = OrderForm(
                                    uid,
                                    CartModel(
                                        it.id,
                                        it.name,
                                        it.imageUrl,
                                        it.price,
                                        it.quantity,
                                        it.color,
                                        it.size
                                    ),
                                    Address.value
                                )
                                ShippingVm.placeOrder(orderForm)
                            }

                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(Color.Gray)
                ) {
                    Text(text = "Continue to shopping", color = Color.White)
                }
            }

        }
    }
}

@Composable
fun ProductList(shippingProduct: List<CartModel>) {
    val dressDescription = shippingProduct

    Column(modifier = Modifier.fillMaxWidth()) {
        dressDescription.forEach {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        modifier = Modifier
                            .height(120.dp)
                            .width(65.dp)
                            .padding(horizontal = 5.dp),
                        shape = RoundedCornerShape(12.dp)
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
                            text = "${it.name}",
                            modifier = Modifier
                                .padding(vertical = 5.dp), fontSize = 12.sp
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "size : ${it.size}",
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
                    Text(
                        text = "${it.price}",
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp), textAlign = TextAlign.End
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }


}

@Composable
fun Billing() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.90.dp)
                .background(Color.Black)
                .padding(top = 3.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(vertical = 3.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Sub Total")
            Text(text = "Rs 5740")
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(vertical = 3.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Shipping")
            Text(text = "Free")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.90.dp)
                .background(Color.Black)
                .padding(top = 3.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Total")
            Text(text = "Rs 5740")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(Color.Black)
                .padding(top = 3.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersoanlInformation(
    addressData: AddressModel,
    address: (address: AddressModel, ischeck: Boolean) -> Unit,
) {
    Log.d("PERSONALINFO","PIF : $addressData ")
    var email by remember {
        mutableStateOf(addressData.email)
    }
    Log.d("PERSONALINFO","PIF Email : ${addressData.email} ")
    Log.d("PERSONALINFO"," PIFEmail : $email ")
    var country by remember {
        mutableStateOf(addressData.country)
    }
    var fname by remember {
        mutableStateOf(addressData.fname)
    }
    var lname by remember {
        mutableStateOf(addressData.lname)
    }
    var address by remember {
        mutableStateOf(addressData.address)
    }
    var city by remember {
        mutableStateOf(addressData.city)
    }
    var pincode by remember {
        mutableStateOf(addressData.pincCode)
    }
    var phoneNo by remember {
        mutableStateOf(addressData.phoneNO)
    }
    var ischeck by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(address, phoneNo, ischeck) {
        address(
            AddressModel(email, country, fname, lname, address, city, pincode, phoneNo),
            ischeck
        )
    }

    Column(Modifier.fillMaxSize()) {
        Column() {
            Text(text = "Contact Information")
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                placeholder = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
        Column {
            Text(text = "Shipping Address")
            OutlinedTextField(
                value = country,
                onValueChange = {
                    country = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = { Text(text = "Country/Region") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(12.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = fname,
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
            OutlinedTextField(
                value = lname,
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

        OutlinedTextField(
            value = address,
            onValueChange = {
                address = it
            },
            placeholder = { Text(text = "Adress") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pink80,
                unfocusedBorderColor = Pink80
            ),
            shape = RoundedCornerShape(18.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
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
            OutlinedTextField(
                value = pincode,
                onValueChange = {
                    pincode = it
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


        OutlinedTextField(
            value = phoneNo,
            onValueChange = {
                phoneNo = it
            },
            placeholder = {
                Text(text = "Contact number")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pink80,
                unfocusedBorderColor = Pink80
            ),
            shape = RoundedCornerShape(18.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = ischeck, onCheckedChange = {
                ischeck = !ischeck
            })
            Text(text = "Save this information for next time")
        }


    }
}