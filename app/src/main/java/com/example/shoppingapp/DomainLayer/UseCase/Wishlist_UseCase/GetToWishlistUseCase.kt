package com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetToWishlistUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun getWishlist(uid:String, productId:String):Flow<ResultState<Boolean>>{
        return repoImpl.GettoWishlist(uid, productId)
    }
}