package com.example.shoppingapp.DomainLayer.UseCase.Profile_UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.ProfileModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfileUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun UpdateProfile(userInfo : ProfileModel):Flow<ResultState<String>>{
        return repoImpl.updateUser(userInfo)
    }
}