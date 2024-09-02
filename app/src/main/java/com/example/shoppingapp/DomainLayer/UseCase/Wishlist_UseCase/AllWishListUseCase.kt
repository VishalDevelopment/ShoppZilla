package com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.CartModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllWishListUseCase @Inject constructor(val repoImpl: RepoImpl){

    fun allWishlist(uid:String):Flow<ResultState<List<CartModel>>>{
        return repoImpl.AllWishList(uid)
    }
}