package com.example.shoppingapp.DomainLayer.UseCase.Product_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getProductUseCase @Inject constructor(val repoImpl: RepoImpl) {
     fun getProduct():Flow<ResultState<List<ProductModel>>>{
       return repoImpl.getProduct()
    }
}