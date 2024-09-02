package com.example.shoppingapp.DomainLayer.Repo

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.TestModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData

interface Repository {
    fun registerUser(signUpModel: SignUpModel):kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun loginUser(loginModel: LoginModel) :kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun getUserByid(uid:String):kotlinx.coroutines.flow.Flow<ResultState<UserParentData>>

    fun getCategory():kotlinx.coroutines.flow.Flow<ResultState<List<CategoryModel>>>
    fun getProduct():kotlinx.coroutines.flow.Flow<ResultState<List<ProductModel>>>
    fun getSpecificProduct(productId:String):kotlinx.coroutines.flow.Flow<ResultState<TestModel>>

    fun AddToCart(uid:String, cartModel: CartModel):kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun GetToCart(uid: String):kotlinx.coroutines.flow.Flow<ResultState<List<CartModel>>>

    fun AddtoWishlist(uid:String,cartModel: CartModel):kotlinx.coroutines.flow.Flow<ResultState<String>>
//    fun GettoWishlist(uid:String,cartModel: CartModel):kotlinx.coroutines.flow.Flow<ResultState<Boolean>>
}