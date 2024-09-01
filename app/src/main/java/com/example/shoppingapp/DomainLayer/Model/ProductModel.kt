package com.example.shoppingapp.DomainLayer.Model

import kotlinx.serialization.Serializable

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

data class TestModel(
    val id:String="",
    val name:String="",
    val description:String="",
    val imageUrl:String="",
    @Serializable
    val actualPrice: Int=0,
    @Serializable
    val discountedPrice: Int =0,
    @Serializable
    val discountedPercentage: Int =0,
    val color: List<String> = mutableListOf(),
    val Size: List<String> = mutableListOf()
)

data class CartModel(
    val id:String="",
    val name:String="",
    val imageUrl:String="",
    val price: Int =0,
    val color: String = "",
    val size: String = ""
)
