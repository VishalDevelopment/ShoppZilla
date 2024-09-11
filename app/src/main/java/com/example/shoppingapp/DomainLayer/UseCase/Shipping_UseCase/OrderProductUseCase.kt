package com.example.shoppingapp.DomainLayer.UseCase.Shipping_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.OrderForm
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import javax.inject.Inject

class OrderProductUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun orderProduct( orderForm: OrderForm):kotlinx.coroutines.flow.Flow<ResultState<String>>{
        return repoImpl.OrderProduct(orderForm)
    }
}