package com.example.shoppingapp.DomainLayer.UseCase.Product_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.TestModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getSpecificProductUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun getSpecificProduct(productId:String):Flow<ResultState<TestModel>>{
        return repoImpl.getSpecificProduct(productId)
    }
}