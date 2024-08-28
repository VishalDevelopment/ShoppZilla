package com.example.shoppingapp.DomainLayer.Model

data class ProductModel(
    val id:String="",
    val name:String="",
    val description:String="",
    val imageUrl:String="",
    val actualPrice: Any? =0,
    val discountedPrice:Any? =0,
    val discount:Any? =0,
    val color: Any? = null,
    val size: Any? =  null
    )
