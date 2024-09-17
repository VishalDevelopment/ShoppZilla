package com.example.shoppingapp.UiLayer.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.shoppingapp.UiLayer.Screens.Auth_Screen.SignUpScreen
import com.example.shoppingapp.UiLayer.Screens.Auth_Screen.SigninScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow

var startDestination :Any = mutableStateOf<Any>(Routes.Auth)

@Composable
fun AppNav(firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()



    NavHost(navController = navController, startDestination = startDestination) {
        navigation<Routes.Auth>(startDestination =Routes.Login) {
            composable<Routes.Login> {
                SigninScreen(navController,firebaseAuth)
            }
            composable<Routes.SignUp> {
                SignUpScreen(navController)
            }
        }

        navigation<Routes.Main>(startDestination = Routes.BottomBar) {
            composable<Routes.BottomBar>{
                BottomBar(firebaseAuth,navController)
            }
        }
    }
}