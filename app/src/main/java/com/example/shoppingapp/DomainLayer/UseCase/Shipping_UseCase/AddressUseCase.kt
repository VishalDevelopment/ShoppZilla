package com.example.shoppingapp.DomainLayer.UseCase.Shipping_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.AddressModel
import java.util.concurrent.Flow
import javax.inject.Inject

class AddressUseCase @Inject constructor(val repoImpl: RepoImpl) {

    fun AddAddress(uid:String , addressModel: AddressModel){
        repoImpl.AddAddress(uid,addressModel)
    }
    fun GetAddress(uid: String):kotlinx.coroutines.flow.Flow<ResultState<AddressModel>>{
        return  repoImpl.GetAddress(uid)
    }
}