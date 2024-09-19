package com.example.shoppingapp.UiLayer.Screens.Shipping_Screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.AddressModel
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.Model.OrderForm
import com.example.shoppingapp.DomainLayer.UseCase.Shipping_UseCase.AddressUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Shipping_UseCase.OrderProductUseCase
import com.example.shoppingapp.UiLayer.Navigation.Routes
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty


@HiltViewModel
class ShippingViewModel @Inject constructor(
    val orderProductUseCase: OrderProductUseCase,
    val addressUseCase: AddressUseCase
) : ViewModel() {
    private val _ShippingList = mutableStateOf(listOf<CartModel>())
    val shippingList = _ShippingList
    fun PushData(ShippingList: List<CartModel>) {
        Log.d("TEST", "$ShippingList")
        _ShippingList.value = ShippingList
    }

    val _orderState = MutableStateFlow<ShippingState>(ShippingState.Loading)
    var orderState =_orderState
    fun placeOrder(orderForm: OrderForm) {
        viewModelScope.launch {
            orderProductUseCase.orderProduct(orderForm).collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        _orderState.value = ShippingState.Error(it.error)
                    }

                    is ResultState.Loading -> {
                        _orderState.value = ShippingState.Loading
                    }

                    is ResultState.Success -> {
                        _orderState.value = ShippingState.Success(it.data)
                    }
                }
            }
        }
    }


    fun Addaddress(uid:String , addressModel: AddressModel){
        addressUseCase.AddAddress(uid, addressModel)
    }

    private  val _AddressData = MutableStateFlow<AddressState>(AddressState.Loading)
    val AddressData = _AddressData

    fun GetAddress(uid:String){
        viewModelScope.launch {
            addressUseCase.GetAddress(uid).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _AddressData.value = AddressState.Error(it.error)
                    }
                    is ResultState.Loading -> {
                        _AddressData.value = AddressState.Loading
                    }
                    is ResultState.Success -> {
                        _AddressData.value = AddressState.Success(it.data)
                    }
                }
            }
        }
    }

}

sealed class AddressState() {
    object Loading : AddressState()
    class Success( val address: AddressModel) :AddressState()
    class Error(val message: String) : AddressState()
}

sealed class ShippingState() {
    object Loading : ShippingState()
    class Success(alert: String) : ShippingState()
    class Error(message: String) : ShippingState()
}