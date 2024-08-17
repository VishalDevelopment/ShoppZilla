package com.example.shoppingapp.DomainLayer.UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getUserByidUseCase@Inject constructor(val repo: RepoImpl) {
    fun getUser(uid:String):Flow<ResultState<UserParentData>>{
        return repo.getUserByid(uid)
    }
}