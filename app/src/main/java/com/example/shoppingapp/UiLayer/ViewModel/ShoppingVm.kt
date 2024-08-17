package com.example.shoppingapp.UiLayer.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import com.example.shoppingapp.DomainLayer.UseCase.LoginUserUseCase
import com.example.shoppingapp.DomainLayer.UseCase.RegisterUserUserCase
import com.example.shoppingapp.DomainLayer.UseCase.getUserByidUseCase
import com.google.firebase.firestore.core.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingVm@Inject constructor(
    private  val registerUserUserCase: RegisterUserUserCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getuserUserUseCase: getUserByidUseCase):ViewModel() {

    val _signupState = MutableStateFlow(SignupScreenState())
    val signupState = _signupState.asStateFlow()

    fun register(signupmodel:SignUpModel){
//        viewModelScope.launch {
//            registerUserUserCase.registerUser(signupmodel).collect{
//                when(it){
//                    is ResultState.Error -> {
//                        _signupState.value = SignupScreenState(error = it.error)
//                    }
//
//                    is ResultState.Success -> {
//                        _signupState.value = SignupScreenState(userData = it.data)
//                    }
//
//                    is ResultState.Loading -> {
//                        _signupState.value = SignupScreenState(isLoading = true)
//                    }
//                }
//            }
//        }
    }
    private  val _loginState = MutableStateFlow(SignupScreenState())
    val loginState = _loginState.asStateFlow()
    fun login(loginModel: LoginModel){
        viewModelScope.launch {
            loginUserUseCase.LoginUser(loginModel).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _loginState.value = SignupScreenState(error = it.error)
                    }
                    is ResultState.Loading -> {
                        _loginState.value = SignupScreenState(isLoading = true)
                    }
                    is ResultState.Success ->{
                        _loginState.value = SignupScreenState(userData = it.data)
                    }
                }
            }
        }
    }

 private    val _userdata = MutableStateFlow(UserDetail())
    val userdata = _userdata.asStateFlow()
    fun userProfileData(uid:String){
        viewModelScope.launch {
            getuserUserUseCase.getUser(uid).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        Log.d("ERROR",UserDetail(error = it.error).toString())
                        _userdata.value = UserDetail(error = it.error)
                    }
                    is ResultState.Loading -> {
                        Log.d("ERROR","LOading")
                        _userdata.value = UserDetail(isLoading = true)

                    }
                    is ResultState.Success -> {
                        Log.d("ERROR","Sucess")
                      _userdata.value = UserDetail(userData = it.data, isLoading = false)
                    }
                }
            }
        }
    }
}


data class UserDetail(
    val isLoading:Boolean = false,
    val error:String  = "",
    val userData: UserParentData=UserParentData()
)


data class SignupScreenState(
    val isLoading:Boolean = false,
    val error:String  ="",
    val userData: String=""
)