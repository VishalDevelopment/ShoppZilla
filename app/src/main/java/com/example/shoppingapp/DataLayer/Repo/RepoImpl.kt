package com.example.shoppingapp.DataLayer.Repo

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CartModel
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.DomainLayer.Model.ProfileComponents
import com.example.shoppingapp.DomainLayer.Model.ProfileModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.TestModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import com.example.shoppingapp.DomainLayer.Repo.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.UUID
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val firebase: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
) : Repository {

    override fun registerUser(signUpModel: SignUpModel): Flow<ResultState<String>> = callbackFlow {
        // Emit the loading state to indicate registration in progress
        trySend(ResultState.Loading())

        firebase.createUserWithEmailAndPassword(signUpModel.email, signUpModel.password)
            .addOnCompleteListener { task -> // Use task for readability
                if (task.isSuccessful) {
                    firebaseFirestore.collection("USERS").document(task.result.user!!.uid)
                        .set(signUpModel).addOnCompleteListener {
                            if (it.isSuccessful) {
                                trySend(ResultState.Success("Registered User"))
                                Log.d("CHECK", "Registered")
                            } else {
                                val errorMessage = task.exception?.localizedMessage
                                    ?: "An error occurred during registration."
                                trySend(ResultState.Error(errorMessage))
                                Log.d("CHECK", "Failed Reg")
                            }
                        }
                    trySend(ResultState.Success("Successfully Created"))
                    Log.d("SUCCESS", "USER CREATED")
                } else {
                    // Handle registration error with a more informative message
                    val errorMessage = task.exception?.localizedMessage
                        ?: "An error occurred during registration."
                    trySend(ResultState.Error(errorMessage))

                    Log.d("ERROR", "ERROR")

                }
            }

        awaitClose {
            close()
        }
    }

    override fun loginUser(loginModel: LoginModel): Flow<ResultState<String>> = callbackFlow {
        firebase.signInWithEmailAndPassword(loginModel.email, loginModel.password)
            .addOnCompleteListener { task ->
                trySend(ResultState.Loading())
                Log.d("REPO", "LOAD : ")
                if (task.isSuccessful) {
                    trySend(ResultState.Success("Login Successfully !!"))
                    Log.d("REPO", "SUCC : ")
                    cancel()
                } else {
                    val errorMessage = task.exception?.localizedMessage
                        ?: "An error occurred during registration."
                    trySend(ResultState.Error(errorMessage))
                    Log.d("REPO", "ERR : ")
                    cancel()
                }
            }
        awaitClose {
            close()
        }
    }

    override fun getUserByid(uid: String): Flow<ResultState<ProfileComponents>> = callbackFlow {
        trySend(ResultState.Loading())
        firebaseFirestore.collection("USERS").document(uid).get().addOnCompleteListener {
            val data = it.result.toObject(ProfileComponents::class.java)

            if (data != null) {
                trySend(ResultState.Success(data))
                close()
            } else {
                if (it.exception != null) {
                    trySend(ResultState.Error(it.exception!!.localizedMessage.toString()))
                    close()
                }
            }
        }
        awaitClose {
            close()
        }
    }

    override fun updateUser(userInfo: ProfileModel):Flow<ResultState<String>>{
      return callbackFlow {
          trySend(ResultState.Loading())
          firebaseFirestore.collection("USERS").document(userInfo.uid).set(userInfo.userInfo)
              .addOnSuccessListener {
              trySend(  ResultState.Success("Successfully Updated !!"))
                  Log.d("UPDATEUSER", "SUCCESS")
              }.addOnFailureListener {
                  trySend(ResultState.Error(it.message.toString()))
              Log.d("UPDATEUSER", "${it.message.toString()}")
          }
          awaitClose {
              close()
          }
      }
    }

    override fun getCategory(): Flow<ResultState<List<CategoryModel>>> = callbackFlow {
        trySend(ResultState.Loading())
        firebaseFirestore.collection("CATEGORY").get().addOnSuccessListener {
            if (it != null) {
                val categoryList = mutableListOf<CategoryModel>()
                for (document in it.documents) {
                    val id = document.getField<String>("id")
                    val name = document.getField<String>("name")
                    val imageUrl = document.getField<String>("imageUrl")
//                    Log.d("CATEGORYDOCREPO "," ${document} ")
                    Log.d("CATEGORYDOCDATAOUT ", " ${id} && ${name} && ${imageUrl} ")
                    if (id != null && name != null && imageUrl != null) {
                        Log.d("CATEGORYDOCDATAIN ", " ${id} && ${name} && ${imageUrl} ")
                        categoryList.add(CategoryModel(id, name, imageUrl))
                    }
                }
                Log.d("CATEGORYRPEO", " $categoryList")
                trySend(ResultState.Success(categoryList))
            }
        }.addOnFailureListener {
            if (it.message != null) {
                trySend(ResultState.Error(it.message.toString()))
            }
        }
        awaitClose {
            close()
        }
    }

    override fun getProduct(): Flow<ResultState<List<ProductModel>>> {
        return callbackFlow {

            trySend(ResultState.Loading())
            firebaseFirestore.collection("PRODUCT").get().addOnSuccessListener {
                val productList = mutableListOf<ProductModel>()
                for (document in it.documents) {
                    val id = document.getField<String>("id")
                    val name = document.getField<String>("name")
                    val image = document.getField<String>("imageUrl")
                    val description = document.getField<String>("description")
                    val category = document.getField<String>("category")

                    val actualPrice = document.get("actualPrice")
                    val discountedPrice = document.get("discountedPrice")
                    val discount = document.get("discountedPercentage")

                    val color = document.get("color")
                    val size = document.get("Size")


                    if (id != null && name != null && description != null && category != null && actualPrice != null && discountedPrice != null && discount != null) {
                        productList.add(
                            ProductModel(
                                id,
                                name,
                                description,
                                image!!,
                                actualPrice,
                                discountedPrice,
                                discount,
                                color,
                                size
                            )
                        )

                    }
                }
                Log.d("PRODUCTDATA", "$productList")
                trySend(ResultState.Success(productList))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
                Log.d("PRODUCTDATA", "${it.message}")
            }
            awaitClose {
                close()
            }
        }
    }

    override fun FilterCategory(categoryName: String): Flow<ResultState<List<TestModel>>> {
        return callbackFlow {
            trySend(ResultState.Loading())
            firebaseFirestore.collection("PRODUCT").get().addOnSuccessListener {
                val ProductModelList = listOf<TestModel>()
                for (document in it.documents){
                    if (document.getField<String>("category") == categoryName){
                        val data = it.toObjects(TestModel::class.java)
                        if (data!=null){
//                            ProductModelList.add(data.)
                        }
                    }
                }
                trySend(ResultState.Success(ProductModelList))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
            awaitClose{
                close()
            }
        }
    }

    override fun getSpecificProduct(productId: String): Flow<ResultState<TestModel>> {
        return callbackFlow {


            trySend(ResultState.Loading())
            firebaseFirestore.collection("PRODUCT").document(productId).get().addOnSuccessListener {

                val productData = it.toObject(TestModel::class.java)
                if (productData != null) {
                    Log.d("FIREPRODUCT", "$productData")
                    trySend(ResultState.Success(productData))

                }
//                }
            }
                .addOnFailureListener {
                    Log.d("REPOIMPL", "${it.message}")
                    trySend(ResultState.Error(it.message.toString()))
                }
            awaitClose {
                close()
            }
        }
    }

    override fun AddToCart(uid: String, cartModel: CartModel): Flow<ResultState<String>> {
        return callbackFlow {
            trySend(ResultState.Loading())
            firebaseFirestore.collection("USERSPECIFIC").document(uid).collection("CART")
                .document(cartModel.id)
                .set(cartModel).addOnSuccessListener {
                    trySend(ResultState.Success("Added to Cart !!"))
                }
                .addOnFailureListener {
                    trySend(ResultState.Error("Error"))
                }
            awaitClose {
                close()
            }
        }
    }

    override fun GetToCart(uid: String): Flow<ResultState<List<CartModel>>> = callbackFlow {
        trySend(ResultState.Loading())
        firebaseFirestore.collection("USERSPECIFIC").document(uid).collection("CART")
            .get().addOnSuccessListener { documents ->
                val cartItem = mutableListOf<CartModel>()
                for (document in documents) {
                    val data = document.toObject(CartModel::class.java)
                    if (data != null) {
                        cartItem.add(data)
                    }
                }
                Log.d("ALLCARTITEM", "$cartItem")
                trySend(ResultState.Success(cartItem))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
                Log.d("GETCART", "${it.message}")
            }
        awaitClose {
            close()
        }
    }

    override fun AddtoWishlist(uid: String, cartModel: CartModel): Flow<ResultState<String>> {
        return callbackFlow {
            trySend(ResultState.Loading())
            firebaseFirestore.collection("USERSPECIFIC").document(uid).collection("WISHLIST")
                .document(cartModel.id)
                .set(cartModel).addOnSuccessListener {
                    trySend(ResultState.Success("Added to Wishlist !!"))
                }
                .addOnFailureListener {
                    trySend(ResultState.Error("Error"))
                }
            awaitClose {
                close()
            }
        }
    }
    override fun GettoWishlist(uid: String, productId: String): Flow<ResultState<Boolean>> {
        return callbackFlow {
            trySend(ResultState.Loading())
            firebaseFirestore.collection("USERSPECIFIC").document(uid).collection("WISHLIST")
                .get().addOnSuccessListener { documents ->
                    val isavailable = mutableStateOf(false)
                    for (document in documents) {
                        if (document != null && productId == document.id) {
                            isavailable.value = true
                        }
                    }
                    Log.d("GOTOWISHLIST", "${isavailable.value}")
                    trySend(ResultState.Success(isavailable.value))
                }
                .addOnFailureListener {
                    trySend(ResultState.Error(it.message.toString()))
                }
            awaitClose {
                close()
            }
        }
    }
    override fun RemoveToWishlist(uid: String, productId: String) {
        firebaseFirestore.collection("USERSPECIFIC").document(uid).collection("WISHLIST")
            .document(productId).delete().addOnSuccessListener {
                Log.d("WISHLIST", "REMOVE TO WISHLIST SUCC :")
            }
            .addOnFailureListener {
                Log.d("WISHLIST", "${it.message}")

            }
    }

    override fun AllWishList(uid: String, ): Flow<ResultState<List<CartModel>>> {
        return callbackFlow {
            trySend(ResultState.Loading())
            firebaseFirestore.collection("USERSPECIFIC").document(uid).collection("WISHLIST").get().addOnSuccessListener {
                val CompleteWishList = mutableListOf<CartModel>()
                for (document in it.documents){
                    val  WishlistItem = document.toObject(CartModel::class.java)
                    if (WishlistItem !=null){
                        CompleteWishList.add(WishlistItem)
                    }
                }
                Log.d("CompleteWishList","$CompleteWishList")
                trySend(ResultState.Success(CompleteWishList))
            }.addOnFailureListener {
                trySend(ResultState.Error(it.message.toString()))
            }
            awaitClose{
                close()
            }
        }
    }

}
