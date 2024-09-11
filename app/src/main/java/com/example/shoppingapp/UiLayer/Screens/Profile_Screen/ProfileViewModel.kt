package com.example.shoppingapp.UiLayer.Screens.Profile_Screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.ProfileComponents
import com.example.shoppingapp.DomainLayer.Model.ProfileModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import com.example.shoppingapp.DomainLayer.UseCase.Profile_UseCase.UpdateProfileUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Profile_UseCase.getUserByidUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getuserUserUseCase: getUserByidUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
) : ViewModel() {
    private val _userdata: MutableStateFlow<UserDetailState> =
        MutableStateFlow(UserDetailState.Loading)
    val userdata = _userdata

    fun userProfileData(uid: String) {
        viewModelScope.launch {
            getuserUserUseCase.getUser(uid).collect {
                when (it) {
                    is ResultState.Error -> {
                        _userdata.value = UserDetailState.Error(message = it.error)

                    }

                    is ResultState.Loading -> {
                        _userdata.value = UserDetailState.Loading

                    }

                    is ResultState.Success -> {
                        _userdata.value = UserDetailState.Success( it.data)
                    }
                }
            }
        }
    }

    val _updateProfileResponse = MutableStateFlow<UpdateProfileState>(UpdateProfileState.Loading)
    val updateProfileResponse = _updateProfileResponse
    fun updateProfileData(userInfo: ProfileModel) {
        viewModelScope.launch {
            updateProfileUseCase.UpdateProfile(userInfo).collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        _updateProfileResponse.value = UpdateProfileState.Error(it.error)
                    }

                    is ResultState.Loading -> {
                        _updateProfileResponse.value = UpdateProfileState.Loading
                    }

                    is ResultState.Success -> {
                        _updateProfileResponse.value = UpdateProfileState.Success(it.data)
                    }
                }
            }
        }
    }
}

sealed class UpdateProfileState {
    object Loading : UpdateProfileState()
    class Error(val message: String) : UpdateProfileState()
    class Success(val message: String) : UpdateProfileState()
}

sealed class UserDetailState {
    object Loading : UserDetailState()
    class Error(val message: String) : UserDetailState()
    class Success(val userInfo: ProfileComponents) : UserDetailState()
}