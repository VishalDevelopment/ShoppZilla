package com.example.shoppingapp.UiLayer.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.R
import com.example.shoppingapp.UiLayer.Navigation.Routes
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import com.example.shoppingapp.ui.theme.Pink80


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {

    val Viewmodel: ShoppingVm = hiltViewModel()
    val state = Viewmodel.signupState.collectAsState()

    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
    val context = LocalContext.current
    if (state.value.error.toString().isNotBlank()) {
        Toast.makeText(context, "${state.value.error}", Toast.LENGTH_SHORT).show()
    }
    else if (state.value.userData!=null){
        Toast.makeText(context, "${state.value.userData}", Toast.LENGTH_SHORT).show()
    }


        var fname by remember { mutableStateOf("") }
        var lname by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmpassword by remember { mutableStateOf("") }



        Box(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ellipse_1_1_),
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
                    text = "SignUp",
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

                Column(
                    Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())) {
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    OutlinedTextField(value = email,
                        onValueChange = {
                            email = it
                        },
                        placeholder = { Text(text = "Email") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Pink80,
                            unfocusedBorderColor = Pink80
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    OutlinedTextField(value = phone,
                        onValueChange = {
                            phone = it
                        },
                        placeholder = { Text(text = "Phone No") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Pink80,
                            unfocusedBorderColor = Pink80
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    OutlinedTextField(value = address,
                        onValueChange = {
                            address = it
                        },
                        placeholder = { Text(text = "Address") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Pink80,
                            unfocusedBorderColor = Pink80
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    OutlinedTextField(value = password,
                        onValueChange = {
                            password = it
                        },
                        placeholder = { Text(text = "Create Password") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Pink80,
                            unfocusedBorderColor = Pink80
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    OutlinedTextField(value = confirmpassword,
                        onValueChange = {
                            confirmpassword = it
                        },
                        placeholder = { Text(text = "Confirm Password") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Pink80,
                            unfocusedBorderColor = Pink80
                        ),
                        shape = RoundedCornerShape(18.dp)
                    )
                    val context = LocalContext.current
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                    Button(
                        onClick = {
                            Toast.makeText(context, "Signup is clicked", Toast.LENGTH_SHORT).show()
                            val userModel = SignUpModel(fname + " "+lname, email,phone,address,confirmpassword)
                           Viewmodel.register(userModel)
                        },
                        colors = ButtonDefaults.buttonColors(Pink80),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Signup")
                    }
                    Spacer(modifier = Modifier.padding(vertical = 4.dp))
                    Text(text = buildAnnotatedString {
                        append("Already have an account ? ")
                        withStyle(SpanStyle(color = Pink80)) {
                            append("Login")
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Routes.Login)
                        }, textAlign = TextAlign.Center)

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .background(Color.Black)
                                .width(150.dp)
                        )
                        Text(text = "OR", color = Color.Black)
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .background(Color.Black)
                                .width(150.dp)
                        )

                    }

                }

            }

    }
}



