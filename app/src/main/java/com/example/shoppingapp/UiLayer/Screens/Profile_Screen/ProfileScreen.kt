package com.example.shoppingapp.UiLayer.Screens.Profile_Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.R
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import com.example.shoppingapp.ui.theme.Pink80
import com.google.firebase.auth.FirebaseAuth


var isDialog by mutableStateOf(false)


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProfileScreen(
    firebaseAuth: FirebaseAuth,
    navHostController: () -> Unit,
) {
    val ProfileVM :ProfileViewModel = hiltViewModel()
    val uid = firebaseAuth.uid.toString()
   LaunchedEffect (key1=true){
       ProfileVM.userProfileData(uid)
   }
    val state = ProfileVM.userdata.collectAsState()

    var fname by remember { mutableStateOf("") }
    var lname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNo by remember{ mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    val EditButtonSelected = remember {
        mutableStateOf(false)
    }

    val ReadOnly by remember {
        if (EditButtonSelected.value == false) {
            mutableStateOf(true)
        } else {
            mutableStateOf(false)
        }
    }

    if (isDialog == true) {
        DialogBox(firebaseAuth, navHostController)
    }
    val context = LocalContext.current
    when (state.value) {
        is UserDetailState.Error -> {
            Toast.makeText(
                context,
                "${(state.value as UserDetailState.Error).message}",
                Toast.LENGTH_SHORT
            ).show()
        }

        is UserDetailState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UserDetailState.Success -> {

            LaunchedEffect(key1 = true) {
            val successData = state.value as? UserDetailState.Success
                successData?.let {
                    Log.d("USERDATA","${it.userParentData.signUpModel}")
                   email = it.userParentData.signUpModel.email
                    var fullName = it.userParentData.signUpModel.name
                    fname = fullName.substringBefore(" ")
                    lname = fullName.substringAfter(" ")
                    phoneNo = it.userParentData.signUpModel.phone
                    address = it.userParentData.signUpModel.address
                }
            }
        }
    }


    var password by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(
                    id =
                    R.drawable.ellipse_1_1_
                ),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Profile",
                color = Color.Black,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Start,
                fontSize = 32.sp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = fname,
                    onValueChange = {
                        fname = it
                    },
                    placeholder = { Text(text = "First Name") },
                    modifier = Modifier
                        .width(150.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Pink80,
                        unfocusedBorderColor = Pink80
                    ),
                    shape = RoundedCornerShape(18.dp), readOnly = ReadOnly
                )
                OutlinedTextField(
                    value = lname,
                    onValueChange = {
                        lname = it
                    },
                    placeholder = { Text(text = "Last Name") },
                    modifier = Modifier
                        .width(150.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Pink80,
                        unfocusedBorderColor = Pink80
                    ),
                    shape = RoundedCornerShape(18.dp), readOnly = ReadOnly
                )
            }

            Column(Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    placeholder = { Text(text = "Email") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Pink80,
                        unfocusedBorderColor = Pink80
                    ),
                    shape = RoundedCornerShape(18.dp), readOnly = ReadOnly
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                OutlinedTextField(
                    value = phoneNo,
                    onValueChange = {
                        phoneNo = it
                    },
                    placeholder = { Text(text = "Phone No ") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Pink80,
                        unfocusedBorderColor = Pink80
                    ),
                    shape = RoundedCornerShape(18.dp), readOnly = ReadOnly
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address= it
                    },
                    placeholder = { Text(text = "Address") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Pink80,
                        unfocusedBorderColor = Pink80
                    ),
                    shape = RoundedCornerShape(18.dp), readOnly = ReadOnly
                )
                val context = LocalContext.current
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Column() {
                    Button(
                        onClick = {
                            if (EditButtonSelected.value == true) {

                                // Save Code

                            }
                            if (EditButtonSelected.value == false) {
                                //Log Out Code
                                isDialog = true
                            }


                        },
                        colors = ButtonDefaults.buttonColors(Pink80),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp), shape = RoundedCornerShape(8.dp)
                    ) {

                        if (EditButtonSelected.value == false) {
                            //Log Out
                            Text(text = "Log Out")
                        }

                        if (EditButtonSelected.value == true) {
                            // Save Code
                            Text(text = "Save")
                        }
                    }
                    Button(
                        onClick = {
                            EditButtonSelected.value = true
                        },
                        colors = ButtonDefaults.buttonColors(Color.White),
                        border = BorderStroke(1.dp, Pink80),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Edit Profile", color = Color.Black)
                    }
                }


            }

        }

    }
}

@Composable

fun DialogBox(firebaseAuth: FirebaseAuth, navHostController: () -> Unit) {
    AlertDialog(icon = {

        Image(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,             // crop the image if it's not a square
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)                       // clip to the circle shape
                .border(2.dp, Color.Gray, CircleShape), contentScale = ContentScale.Fit
        )
    }, text = {
        Text(
            text = "Do You Really Want to Logout ?",
            fontSize = 20.sp,
            textAlign = TextAlign.Center, color = Color.Black
        )
    }, title = {
        Text(
            text = "Log Out",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center, color = Pink80
        )
    },
        onDismissRequest = {
            isDialog = false
        },
        confirmButton = {
            OutlinedButton(
                onClick = {
                    firebaseAuth.signOut()
                    navHostController()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Pink80),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Text(text = "LogOut", fontWeight = FontWeight.Medium)
            }
        }, dismissButton = {
            OutlinedButton(
                onClick = {
                    isDialog = false
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Pink80)
            ) {
                Text(text = "Cancel", color = Pink80)
            }
        }
    )
}