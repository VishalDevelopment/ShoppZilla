package com.example.shoppingapp.UiLayer.Screens.Search_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.DomainLayer.UseCase.Product_UseCase.getProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
private val getProductUseCase: getProductUseCase
):ViewModel() {

    private val _product = MutableStateFlow<ProductState>(ProductState.Loading)
    val  product = _product

    fun getProducts(){
        viewModelScope.launch {
            getProductUseCase.getProduct().collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _product.value = ProductState.Error(it.error)
                    }
                    is ResultState.Loading -> {
                        _product.value = ProductState.Loading

                    }
                    is ResultState.Success -> {

                        _product.value = ProductState.Success(it.data)
                    }
                }
            }
        }
    }
}

sealed class ProductState{
    object Loading:ProductState()
    data class Error(val message: String) : ProductState()
data class Success(val data: List<ProductModel>):ProductState()
}