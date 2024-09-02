package com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase

import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import javax.inject.Inject

class RemoveToWishlistUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun removetowishlist(uid:String, productId:String){
        repoImpl.RemoveToWishlist(uid,productId)
    }
}