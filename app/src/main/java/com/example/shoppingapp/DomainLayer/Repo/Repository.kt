package com.example.shoppingapp.DomainLayer.Repo

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import java.util.concurrent.Flow

interface Repository {
    fun registerUser(signUpModel: SignUpModel):kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun loginUser(loginModel: LoginModel) :kotlinx.coroutines.flow.Flow<ResultState<String>>
    fun getUserByid(uid:String):kotlinx.coroutines.flow.Flow<ResultState<UserParentData>>
}