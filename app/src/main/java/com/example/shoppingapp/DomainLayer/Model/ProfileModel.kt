package com.example.shoppingapp.DomainLayer.Model

data class ProfileModel(
    val uid:String ,
    val userInfo:ProfileComponents
)

data class ProfileComponents(
    val name : String="",
    val email:String ="",
    val phoneNo:String="" ,
    val address:String=""
)