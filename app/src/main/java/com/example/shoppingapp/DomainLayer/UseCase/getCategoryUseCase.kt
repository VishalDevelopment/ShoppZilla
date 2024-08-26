package com.example.shoppingapp.DomainLayer.UseCase

import com.example.shoppingapp.CommonState.ResultState
import com.example.shoppingapp.DataLayer.Repo.RepoImpl
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Repo.Repository
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getCategoryUseCase @Inject constructor(val repoImpl: RepoImpl) {
    fun getCategory(): Flow<ResultState<List<CategoryModel>>> {
       return repoImpl.getCategory()
    }
}