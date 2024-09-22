package com.example.shoppingapp.UiLayer.Screens.Search_Screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.shoppingapp.DomainLayer.Model.SearchModel
import com.example.shoppingapp.ui.theme.Pink80
import java.util.Locale

@Composable
fun SearchScreen() {

    val SearchVM: SearchViewModel = hiltViewModel()

    LaunchedEffect(key1 = Unit) {
        SearchVM.getProducts()
    }

    val AllProducts = SearchVM.product.collectAsState()
    val productList = remember{
        mutableStateOf(mutableListOf(SearchModel()))
    }


    val search = remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBarLayout() {
                search.value = it
            }
            Spacer(modifier = Modifier.height(10.dp))

            when (AllProducts.value) {
                is ProductState.Error -> {
                  Log.d("ERROR","ERR : ")
                }

                is ProductState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        CircularProgressIndicator()
                    }
                }

                is ProductState.Success -> {
                    val productdata = (AllProducts.value as ProductState.Success).data
                    productdata.forEach {
                        productList.value.add(SearchModel(it.imageUrl,it.name,it.discountedPrice))
                    }
                    Log.d("DATA","${productList.value}")

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter){

                    SearchResult(productList, search)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarLayout(search: (search: String) -> Unit) {
    var searchContent by remember {
        mutableStateOf("")
    }
    LaunchedEffect(searchContent) {
        search(searchContent)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedTextField(
            value = searchContent,
            onValueChange = {
                searchContent = it
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            placeholder = { Text(text = "Search") },
            modifier = Modifier
                .fillMaxWidth(.90f)
                .padding(top = 10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pink80,
                unfocusedBorderColor = Pink80
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun SearchResult(dataItem: MutableState<MutableList<SearchModel>>, search: MutableState<String>) {


    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val filterList = if (search.value.isEmpty()) {
            dataItem.value
        } else {
            val result = mutableListOf<SearchModel>()
            dataItem.value.forEach{
                if (it.name?.toLowerCase(Locale.getDefault())
                        ?.contains(search.value.toLowerCase(Locale.getDefault())) == true
                ) {
                    result.add(SearchModel(it.imageUrl, it.name, it.price))
                }
            }
            result
        }
        items(filterList) {
            if (it.name !=null && it.imageUrl !=null){
                CustomLayoutForSearch(it.name, it.imageUrl, it.price)
            }
        }
    }
}

//
@Composable
fun CustomLayoutForSearch(name: String, imageUrl: String, price: Any?) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .padding(horizontal = 5.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    Modifier.fillMaxSize(), contentScale = ContentScale.Crop
                )
            }
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 3.dp)
                        .fillMaxWidth(),
                    fontSize = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }

        Text(
            text = " $price",
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 20.dp)
                .weight(1f)
        )
    }
}