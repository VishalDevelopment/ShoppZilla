package com.example.shoppingapp.DomainLayer.UseCase.Category_Model

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterCategoryUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun filterList(categoryName:String):Flow<ResultState<List<ProductModel>>>{
        return repoImpl.FilterCategory(categoryName)
    }
}