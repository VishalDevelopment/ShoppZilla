package com.example.shoppingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppingapp.UiLayer.Navigation.AppNav
import com.example.shoppingapp.UiLayer.Screens.Category_Screen.CategoryVm
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(),PaymentResultListener {

        @Inject
        lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ShoppingAppTheme {
                val ShoppingVm: ShoppingVm = hiltViewModel()

                installSplashScreen().apply{
                    setKeepOnScreenCondition{
                        ShoppingVm.splash
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)){

                        AppNav(firebaseAuth)
                    }
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Log.d("STATUS","COMPLETED : $p0")
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Log.d("STATUS","FAILED : $p1")
    }
}
