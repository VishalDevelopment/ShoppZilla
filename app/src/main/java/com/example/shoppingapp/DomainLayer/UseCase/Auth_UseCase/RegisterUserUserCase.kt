package com.example.shoppingapp.DomainLayer.UseCase.Auth_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import javax.inject.Inject

class RegisterUserUserCase@Inject constructor(private  val repo :RepoImpl) {
    fun registerUser(signUpModel:SignUpModel):kotlinx.coroutines.flow.Flow<ResultState<String>> {
        return repo.registerUser(signUpModel)
    }
}