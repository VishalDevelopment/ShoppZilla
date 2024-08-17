package com.example.shoppingapp.UiLayer.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.shoppingapp.UiLayer.Screens.SignUpScreen
import com.example.shoppingapp.UiLayer.Screens.SigninScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNav(firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
//    val startdestination = if (firebaseAuth.currentUser == null) {
//        Routes.Auth
//    } else  {
//        Routes.Main
//    }

    NavHost(navController = navController, startDestination = Routes.Auth) {
        navigation<Routes.Auth>(startDestination =Routes.Login) {
            composable<Routes.Login> {
                SigninScreen(navController)
            }
            composable<Routes.SignUp> {
                SignUpScreen(navController)
            }
        }

        navigation<Routes.Main>(startDestination = Routes.BottomBar) {
            composable<Routes.BottomBar>{
                BottomBar(firebaseAuth)
            }
        }
    }
}