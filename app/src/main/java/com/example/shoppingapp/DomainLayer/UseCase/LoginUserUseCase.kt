package com.example.shoppingapp.DomainLayer.UseCase

import android.util.Log
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Repo.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase@Inject constructor(val repository: RepoImpl) {
    fun LoginUser(loginModel:LoginModel):Flow<ResultState<String>>{
        Log.d("LOGINUC","PASS : ")
      return  repository.loginUser(loginModel)
    }
}