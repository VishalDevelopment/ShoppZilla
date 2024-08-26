package com.example.shoppingapp.DataLayer.Repo

import android.util.Log
import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Model.LoginModel
import com.example.shoppingapp.DomainLayer.Model.SignUpModel
import com.example.shoppingapp.DomainLayer.Model.UserParentData
import com.example.shoppingapp.DomainLayer.Repo.Repository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.getField
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    override fun getUserByid(uid: String): Flow<ResultState<UserParentData>> = callbackFlow {
        trySend(ResultState.Loading())
        firebaseFirestore.collection("USERS").document(uid).get().addOnCompleteListener {
            val data = it.result.toObject(SignUpModel::class.java)
            val userParentData = UserParentData(it.result.id, data!!)
            if (data != null) {
                trySend(ResultState.Success(userParentData))
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

    override fun getCategory(): Flow<ResultState<List<CategoryModel>>> = callbackFlow {
        trySend(ResultState.Loading())
        firebaseFirestore.collection("CATEGORY").get().addOnSuccessListener {
            if (it!=null) {
                val categoryList = mutableListOf<CategoryModel>()
                for (document in it.documents){
                    val id = document.getField<String>("id")
                    val name = document.getField<String>("name")
                    val  imageUrl = document.getField<String>("imageUrl")
//                    Log.d("CATEGORYDOCREPO "," ${document} ")
                    Log.d("CATEGORYDOCDATAOUT "," ${id} && ${name} && ${imageUrl} ")
                    if (id != null && name != null && imageUrl != null) {
                        Log.d("CATEGORYDOCDATAIN "," ${id} && ${name} && ${imageUrl} ")
                        categoryList.add(CategoryModel(id, name, imageUrl))
                    }
                }
                Log.d("CATEGORYRPEO"," $categoryList")
                trySend(ResultState.Success(categoryList))
            }
        }.addOnFailureListener {
                if (it.message!=null) {
                    trySend(ResultState.Error(it.message.toString()))
            }
        }
        awaitClose {
            close()
        }
    }
}