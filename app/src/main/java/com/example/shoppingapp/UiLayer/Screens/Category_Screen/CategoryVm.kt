package com.example.shoppingapp.UiLayer.Screens.Category_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.DomainLayer.Model.TestModel
import com.example.shoppingapp.DomainLayer.UseCase.Category_Model.FilterCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryVm @Inject constructor(
    private val filterCategoryUseCase: FilterCategoryUseCase
):ViewModel() {

   private val _filterCategory = MutableStateFlow<FilterCategoryState>(FilterCategoryState.Loading)
    val filterCategory = _filterCategory
    fun filterCategory(categoryName:String){
        viewModelScope.launch {
            filterCategoryUseCase.filterList(categoryName).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _filterCategory.value = FilterCategoryState.Error(it.error)
                    }
                    is ResultState.Loading -> {
                        _filterCategory.value = FilterCategoryState.Loading
                    }
                    is ResultState.Success -> {
                        _filterCategory.value = FilterCategoryState.Success(it.data)
                    }
                }
            }
        }
    }
}

sealed class FilterCategoryState(){
    object Loading: FilterCategoryState()
    data class Success(val filterCategory:List<ProductModel>): FilterCategoryState()
    data class Error(val message:String): FilterCategoryState()
}