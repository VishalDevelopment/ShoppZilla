package com.example.shoppingapp.UiLayer.Navigation

import kotlinx.serialization.Serializable


@Serializable
sealed class Routes(val route:String){

    @Serializable
    object Login
    @Serializable
    object SignUp
    @Serializable
    object Home
    @Serializable
    object Cart
    @Serializable
    object Wishlist
    @Serializable
    object Profile
    @Serializable
    object ProductDetail
    @Serializable
    object Checkout
    @Serializable
    //Nav Graph
    object Auth
    @Serializable
    object Main
    @Serializable
    object BottomBar
}