package com.example.shoppingapp.UiLayer.Screens.Cart_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.UseCase.addProductCart
import com.example.shoppingapp.DomainLayer.UseCase.getProductcart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val addProductCart: addProductCart,
    val getProductcart: getProductcart
) :ViewModel(){

    val _cartResponse = MutableStateFlow<CartState>(CartState.Loading)
    val cartResponse = _cartResponse

    fun addtoCart(uid:String, cartModel: CartModel){
        viewModelScope.launch {
            addProductCart.addToCart(uid, cartModel).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _cartResponse.value = CartState.Error(it.error)
                    }
                    is ResultState.Loading -> {
                        _cartResponse.value = CartState.Loading
                    }
                    is ResultState.Success -> {
                        _cartResponse.value = CartState.Success(it.data)
                    }
                }
            }
        }
    }

    val _cartItem = MutableStateFlow<ReceiveCartState>(ReceiveCartState.Loading)
    val cartItem = _cartItem
    fun getProductCart(uid:String){
        viewModelScope.launch {
            getProductcart.getCart(uid).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _cartItem.value = ReceiveCartState.Error(it.error)
                    }
                    is ResultState.Loading -> {
                        _cartItem.value =  ReceiveCartState.Loading
                    }
                    is ResultState.Success -> {
                        _cartItem.value =  ReceiveCartState.Success(it.data)
                    }
                }
            }
        }
    }





}

sealed class CartState(){
    object Loading: CartState()
    data class Error(val message:String): CartState()
    data class Success(val message: String):CartState()
}

sealed class ReceiveCartState(){
    object Loading: ReceiveCartState()
    data class Error(val message:String): ReceiveCartState()
    data class Success(val cartList: List<CartModel>):ReceiveCartState()
}