package com.example.shoppingapp.DomainLayer.Model

data class OrderForm(
    val uid:String,
    val OrderDetail:CartModel,
    val address:AddressModel
)
