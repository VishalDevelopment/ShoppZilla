package com.example.shoppingapp.UiLayer.Navigation

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable


@Serializable
sealed class Routes{

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
    data class Category(val categoryName:String)
    @Serializable
    data class ProductDetail(val productId: String)
    @Serializable
    object Checkout

    @Serializable
    class Shipping(val Flag: Int)
    @Serializable
    //Nav Graph
    object Auth
    @Serializable
    object Main
    @Serializable
    object BottomBar
}