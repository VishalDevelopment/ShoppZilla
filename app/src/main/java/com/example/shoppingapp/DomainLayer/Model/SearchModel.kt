package com.example.shoppingapp.DomainLayer.Model

data class SearchModel(
    val productId:String?=null,
    val imageUrl: String?=null,
    val name:String?=null,
    val price: Any? =null
)
