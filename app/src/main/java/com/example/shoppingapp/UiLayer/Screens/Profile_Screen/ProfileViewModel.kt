package com.example.shoppingapp.UiLayer.Screens.Profile_Screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import com.example.shoppingapp.DomainLayer.UseCase.getUserByidUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getuserUserUseCase: getUserByidUseCase
):ViewModel() {
    private val _userdata: MutableStateFlow<UserDetailState> =
        MutableStateFlow(UserDetailState.Loading)
    val userdata = _userdata

    fun userProfileData(uid: String) {
        viewModelScope.launch {
            getuserUserUseCase.getUser(uid).collect {
                when (it) {
                    is ResultState.Error -> {
                        Log.d("ERROR", UserDetailState.Error(message = it.error).message)
                        _userdata.value = UserDetailState.Error(message = it.error)

                    }

                    is ResultState.Loading -> {
                        Log.d("LOAD", "LOAD : ")
                        _userdata.value = UserDetailState.Loading

                    }

                    is ResultState.Success -> {
                        Log.d("SUCCESS", "SUCC : ")
                        _userdata.value = UserDetailState.Success(userParentData = it.data)
                    }
                }
            }
        }
    }
}

sealed class UserDetailState {
    object Loading : UserDetailState()
    class Error(val message: String) : UserDetailState()
    class Success(val userParentData: UserParentData) : UserDetailState()
}