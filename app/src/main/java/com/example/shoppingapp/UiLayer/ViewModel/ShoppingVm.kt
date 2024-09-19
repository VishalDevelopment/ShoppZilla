package com.example.shoppingapp.UiLayer.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.TestModel
import com.example.shoppingapp.DomainLayer.UseCase.Auth_UseCase.LoginUserUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Auth_UseCase.RegisterUserUserCase
import com.example.shoppingapp.DomainLayer.UseCase.Category_Model.getCategoryUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Product_UseCase.getProductUseCase
import com.example.shoppingapp.DomainLayer.UseCase.Product_UseCase.getSpecificProductUseCase
import com.example.shoppingapp.UiLayer.Navigation.Routes
import com.example.shoppingapp.UiLayer.Navigation.startDestination
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    private val getCategoryUseCase: getCategoryUseCase,
    private val getProductUseCase: getProductUseCase,
    private val getSpecificProductUseCase: getSpecificProductUseCase,
    val firebaseAuth: FirebaseAuth,
) : ViewModel() {


    var splash by mutableStateOf(true)

    init {
        viewModelScope.launch {
        val user: FirebaseUser? = firebaseAuth.currentUser

            if (user == null) {
                Log.d("SHOPVM","NULL : $user")
                startDestination=   Routes.Auth
            } else  {
                Log.d("SHOPVM","NOT NULL : $user")
                startDestination=  Routes.Main
            }
            delay(500)
            splash = false
        }
    }

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
                    }

                    is ResultState.Loading -> {
                        _loginState.value = LoginState.Loading
                    }

                    is ResultState.Success -> {
                        _loginState.value = LoginState.Succes
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

    private val _productdata = MutableStateFlow<SpecificProduct>(SpecificProduct.Loading)
    val productdata = _productdata
    fun getSpecificProduct(productID: String) {
        viewModelScope.launch {
            getSpecificProductUseCase.getSpecificProduct(productID).collectLatest {
                when (it) {
                    is ResultState.Error -> {
                        _productdata.value = SpecificProduct.Error(it.error)
                    }

                    is ResultState.Loading -> {
                        _productdata.value = SpecificProduct.Loading
                    }

                    is ResultState.Success -> {
                        _productdata.value = SpecificProduct.Success(it.data)
                    }
                }
            }
        }
    }


}

sealed class SpecificProduct {
    class Success(val product: TestModel) : SpecificProduct()
    class Error(message: String) : SpecificProduct()
    object Loading : SpecificProduct()
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


data class SignupScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val userData: String = "",
)