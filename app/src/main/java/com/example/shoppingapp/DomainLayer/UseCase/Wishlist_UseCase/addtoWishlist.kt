package com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class addtoWishlistUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun addtoWishlist(uid:String, cartModel: CartModel): Flow<ResultState<String>>{
        return repoImpl.AddtoWishlist(uid,cartModel)
    }
}