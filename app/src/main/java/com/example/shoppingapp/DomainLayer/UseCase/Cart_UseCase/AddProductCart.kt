package com.example.shoppingapp.DomainLayer.UseCase.Cart_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class addProductCart@Inject constructor(val repoImpl: RepoImpl) {
    fun addToCart(uid:String, cartModel: CartModel):Flow<ResultState<String>>{
        return repoImpl.AddToCart(uid, cartModel)
    }
}