package com.example.shoppingapp.DomainLayer.UseCase.Cart_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveToCartUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun RemoveToCart(uid:String , productId:String):Flow<ResultState<String>>{
        return repoImpl.RemoveToCart(uid, productId)
    }
}