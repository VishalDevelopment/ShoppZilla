package com.example.shoppingapp.DomainLayer.Repo

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.AddressModel
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.OrderForm
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.DomainLayer.Model.ProfileComponents
import com.example.shoppingapp.DomainLayer.Model.ProfileModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.TestModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun registerUser(signUpModel: SignUpModel):kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun loginUser(loginModel: LoginModel) :kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun getUserByid(uid:String):kotlinx.coroutines.flow.Flow<ResultState<ProfileComponents>>
    fun updateUser(userInfo : ProfileModel):Flow<ResultState<String>>

    fun getCategory():kotlinx.coroutines.flow.Flow<ResultState<List<CategoryModel>>>
    fun getProduct():kotlinx.coroutines.flow.Flow<ResultState<List<ProductModel>>>

    fun FilterCategory(categoryName:String):Flow<ResultState<List<ProductModel>>>
    fun getSpecificProduct(productId:String):kotlinx.coroutines.flow.Flow<ResultState<TestModel>>

    fun AddToCart(uid:String, cartModel: CartModel):kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun GetToCart(uid: String):kotlinx.coroutines.flow.Flow<ResultState<List<CartModel>>>
    fun RemoveToCart(uid:String , productId: String):Flow<ResultState<String>>

    fun AddtoWishlist(uid:String,cartModel: CartModel):kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun GettoWishlist(uid:String,productId: String):kotlinx.coroutines.flow.Flow<ResultState<Boolean>>
    fun RemoveToWishlist(uid:String , productId: String)
    fun AllWishList (uid: String):Flow<ResultState<List<CartModel>>>


    fun OrderProduct(orderData : OrderForm):Flow<ResultState<String>>
    fun AddAddress(uid:String , address:AddressModel)
    fun GetAddress(uid:String):Flow<ResultState<AddressModel>>

}