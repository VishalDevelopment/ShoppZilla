package com.example.shoppingapp.UiLayer.Screens.Wishlist_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.UseCase.addtoWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(val addtoWishlistUseCase: addtoWishlistUseCase):ViewModel(){

    private val _addWishlist = MutableStateFlow<WishListState>(WishListState.Loading)
    val addWishlist = _addWishlist
    fun addtoWishList(uid:String , cartModel: CartModel){
        viewModelScope.launch {
            addtoWishlistUseCase.addtoWishlist(uid, cartModel).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _addWishlist.value = WishListState.Error(it.error)
                    }
                    is ResultState.Loading -> {
                        _addWishlist.value = WishListState.Loading
                    }
                    is ResultState.Success -> {
                        _addWishlist.value = WishListState.Success(it.data)
                    }
                }
            }
        }
    }

}

sealed class WishListState{
    object Loading: WishListState()
    data class Error(val message:String): WishListState()
    data class Success(val message: String):WishListState()
}