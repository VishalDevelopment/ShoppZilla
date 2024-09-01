package com.example.shoppingapp.DomainLayer.UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getProductcart @Inject constructor(val repoImpl: RepoImpl) {
    fun getCart(uid: String): Flow<ResultState<List<CartModel>>> {
        return repoImpl.GetToCart(uid)
    }
}