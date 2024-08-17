package com.example.shoppingapp.UiLayer.Screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import com.example.shoppingapp.R
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import com.example.shoppingapp.ui.theme.Pink80
import com.google.firebase.auth.FirebaseAuth
import com.google.firestore.v1.TransactionOptions.ReadOnly
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProfileScreen(firebaseAuth: FirebaseAuth, viewmodel: ShoppingVm = hiltViewModel()) {
//    val uid = firebaseAuth.uid.toString()


//    viewmodel.userProfileData()
//    val state = viewmodel.userdata.collectAsState()


    var fname by remember { mutableStateOf("") }
    var lname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    val EditButtonSelected = remember {
        mutableStateOf(false)
    }

    val ReadOnly by remember{
        if (EditButtonSelected.value == false) {
            mutableStateOf(true)
        } else {
            mutableStateOf(false)
        }
    }
//    if (state.value.userData != UserParentData() && state.value.userData!!.signUpModel != SignUpModel("","","")) {
//        Log.d("USERDATAOUTLAUNCH","${state.value.userData}")
//        LaunchedEffect(key1 = true) {
//            Log.d("USERDATAINLAUNCH","${state.value.userData}")
//            email = state.value.userData!!.signUpModel!!.email
//            var name = state.value.userData.signUpModel.name
//            val split_name = name.split(" ")
//            fname = split_name.first()
//            lname = split_name.last()
//            Log.d("USERINFO", "${state.value.userData!!.signUpModel}")
//        }
//    }


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
                .padding(30.dp),
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
                        .width(150.dp)
                        .padding(horizontal = 8.dp),
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
                        .width(150.dp)
                        .padding(horizontal = 8.dp),
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
                    value = password,
                    onValueChange = {
                        password = it
                    },
                    placeholder = { Text(text = "Create Password") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Pink80,
                        unfocusedBorderColor = Pink80
                    ),
                    shape = RoundedCornerShape(18.dp), readOnly = ReadOnly
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                OutlinedTextField(
                    value = confirmpassword,
                    onValueChange = {
                        confirmpassword = it
                    },
                    placeholder = { Text(text = "Confirm Password") },
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

                            if (EditButtonSelected.value == false){
                                EditButtonSelected.value = true
                            }

                            if (EditButtonSelected.value == false){
                                // Save Code

                                EditButtonSelected.value = true
                            }


                        },
                        colors = ButtonDefaults.buttonColors(Pink80),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp), shape = RoundedCornerShape(8.dp)
                    ) {

                        if (EditButtonSelected.value == false){
                            //Log Out
                            Text(text = "Log Out")
                        }

                        if (EditButtonSelected.value == true){
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