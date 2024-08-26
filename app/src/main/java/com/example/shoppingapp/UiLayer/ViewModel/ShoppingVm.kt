package com.example.shoppingapp.UiLayer.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import com.example.shoppingapp.DomainLayer.UseCase.LoginUserUseCase
import com.example.shoppingapp.DomainLayer.UseCase.RegisterUserUserCase
import com.example.shoppingapp.DomainLayer.UseCase.getCategoryUseCase
import com.example.shoppingapp.DomainLayer.UseCase.getProductUseCase
import com.example.shoppingapp.DomainLayer.UseCase.getUserByidUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingVm @Inject constructor(
    private val registerUserUserCase: RegisterUserUserCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getuserUserUseCase: getUserByidUseCase,
    private val getCategoryUseCase: getCategoryUseCase,
    private val getProductUseCase: getProductUseCase,
) : ViewModel() {

    val _signupState = MutableStateFlow(SignupScreenState())
    val signupState = _signupState.asStateFlow()

    fun register(signupmodel: SignUpModel) {
        viewModelScope.launch {
            registerUserUserCase.registerUser(signupmodel).collect {
                when (it) {
                    is ResultState.Error -> {
                        _signupState.value = SignupScreenState(error = it.error)
                    }

                    is ResultState.Success -> {
                        _signupState.value = SignupScreenState(userData = it.data)
                    }

                    is ResultState.Loading -> {
                        _signupState.value = SignupScreenState(isLoading = true)
                    }
                }
            }
        }
    }

    private val _loginState = MutableStateFlow<LoginState?>(null)
    val loginState: StateFlow<LoginState?> = _loginState
    fun login(loginModel: LoginModel) {
        viewModelScope.launch {
            loginUserUseCase.LoginUser(loginModel).collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        _loginState.value = LoginState.Error(it.error)
                        Log.d("VM", "ERR : ")

                    }

                    is ResultState.Loading -> {
                        _loginState.value = LoginState.Loading
                        Log.d("VM", "LOAD : ")
                    }

                    is ResultState.Success -> {
                        _loginState.value = LoginState.Succes
                        Log.d("VM", "SUCC : ")
                    }
                }
            }
        }
    }

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

    val CategoriesData = MutableStateFlow<CategoryState>(CategoryState.Loading)
    fun getCategory() {
        viewModelScope.launch {
            getCategoryUseCase.getCategory().collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        CategoriesData.value = CategoryState.Error(it.error)
                    }

                    is ResultState.Loading -> {
                        CategoriesData.value = CategoryState.Loading
                    }

                    is ResultState.Success -> {
                        Log.d("CATEGORYVM", "${it.data}")
                        CategoriesData.value = CategoryState.Succes(it.data)
                    }
                }
            }
        }
    }

    private val _productList = MutableStateFlow<ProductState>(ProductState.Loading)
    val productList = _productList
    fun getProduct() {
        viewModelScope.launch {
            getProductUseCase.getProduct().collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        productList.value = ProductState.Error(it.error)
                    }

                    is ResultState.Loading -> {
                        productList.value = ProductState.Loading
                    }

                    is ResultState.Success -> {
                        productList.value = ProductState.Success(it.data)
                    }
                }
            }
        }
    }

}

sealed class ProductState {
    class Success(val product: List<ProductModel>) : ProductState()
    class Error(message: String) : ProductState()
    object Loading : ProductState()
}

sealed class CategoryState {
    class Succes(val category: List<CategoryModel>) : CategoryState()
    class Error(message: String) : CategoryState()
    object Loading : CategoryState()
}

sealed class LoginState {
    object Succes : LoginState()
    class Error(message: String) : LoginState()
    object Loading : LoginState()
}

sealed class UserDetailState {
    object Loading : UserDetailState()
    class Error(val message: String) : UserDetailState()
    class Success(val userParentData: UserParentData) : UserDetailState()
}


data class SignupScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val userData: String = "",
)