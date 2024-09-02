package com.example.shoppingapp.UiLayer.Screens.Wishlist_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase.AllWishListUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase.GetToWishlistUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase.RemoveToWishlistUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Wishlist_UseCase.addtoWishlistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val addtoWishlistUseCase: addtoWishlistUseCase,
    private val getToWishlistUseCase: GetToWishlistUseCase,
    private val removeToWishlistUseCase: RemoveToWishlistUseCase,
    private val allWishListUseCase: AllWishListUseCase
):ViewModel(){

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

    private val _isWishlistAvailable = MutableStateFlow<WishListReceiveState>(WishListReceiveState.Loading)
   val  isWishlistAvailable = _isWishlistAvailable

    fun gettoWishlist(uid: String, productId:String){
        viewModelScope.launch {
            getToWishlistUseCase.getWishlist(uid, productId).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _isWishlistAvailable.value =WishListReceiveState.Error(it.error)
                    }
                    is ResultState.Loading -> {
                        _isWishlistAvailable.value = WishListReceiveState.Loading
                    }
                    is ResultState.Success -> {
                        _isWishlistAvailable.value = WishListReceiveState.Success(it.data)
                    }
                }
            }
        }
    }

    fun removetoWishlist(uid: String,productId: String){
        removeToWishlistUseCase.removetowishlist(uid, productId)
    }

    private val _CompleteWishList = MutableStateFlow<CompleteWishListState>(CompleteWishListState.Loading)
    val  CompleteWishList = _CompleteWishList
    fun AllWishListItem(uid: String){
        viewModelScope.launch {
            allWishListUseCase.allWishlist(uid).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _CompleteWishList.value = CompleteWishListState.Error(it.error.toString())
                    }
                    is ResultState.Loading -> {
                        _CompleteWishList.value = CompleteWishListState.Loading
                    }
                    is ResultState.Success -> {
                        _CompleteWishList.value = CompleteWishListState.Success(it.data)
                    }
                }
            }
        }
    }
}

sealed class CompleteWishListState{
    object Loading:CompleteWishListState()
    data class Success(val wishlist :List<CartModel>):CompleteWishListState()
    data class Error(val message:String):CompleteWishListState()
}
sealed class WishListReceiveState{
    object Loading: WishListReceiveState()
    data class Error(val message:String): WishListReceiveState()
    data class Success(val isAvailable: Boolean):WishListReceiveState()
}
sealed class WishListState{
    object Loading: WishListState()
    data class Error(val message:String): WishListState()
    data class Success(val message: String):WishListState()
}