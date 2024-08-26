package com.example.shoppingapp.UiLayer.Screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.R
import com.example.shoppingapp.UiLayer.ViewModel.CategoryState
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import com.example.shoppingapp.ui.theme.Pink80

@Composable
fun HomeScreen() {
    val categoryData = remember{ mutableStateOf(emptyList<CategoryModel>()) }
    val viewmodel: ShoppingVm = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        viewmodel.getCategory()
    }
    val categorystate = viewmodel.CategoriesData.collectAsState()

    when (categorystate.value) {
        is CategoryState.Error -> {
            Log.d("HOME", "Error")
        }

        CategoryState.Loading -> {
            Log.d("HOME", "LOADING")

        }

        is CategoryState.Succes -> {
               val categoryList = (categorystate.value as CategoryState.Succes).category// Access the list
            categoryData.value = categoryList
    }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SearchBar()
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OptionHead("Categories", "See More")
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Category(categoryData)
            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            OptionHead(title = "Flash Sale", moreData = "See More")
            ShoppingList()
        }

    }
}

@Composable
fun OptionHead(title: String, moreData: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 20.sp
        )
        Text(
            text = moreData,
            color = Pink80,
            modifier = Modifier.padding(horizontal = 10.dp),
            fontSize = 20.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
    var searchContent by remember {
        mutableStateOf("")
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 6.dp, vertical = 5.dp),
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
            modifier = Modifier.fillMaxWidth(.90f),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Pink80,
                unfocusedBorderColor = Pink80
            ),
            shape = RoundedCornerShape(12.dp)
        )
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
    }
}

@Composable
fun Category(categoryData: MutableState<List<CategoryModel>>) {

    val CategoryImg =
        listOf(categoryData.value, R.drawable.underwear, R.drawable.jeans, R.drawable.t_shirt)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(categoryData.value) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(100.dp)
                    .paint( // Replace with your image id
                        painterResource(id = R.drawable.circle__transparent_),
                        contentScale = ContentScale.FillBounds
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CategoryItem(it)
                }
            }
        }

    }
}

@Composable
fun CategoryItem(categoryModel: CategoryModel) {

    Log.d("CATEGORYITEM","${categoryModel.imageUrl}")
    val painter = rememberAsyncImagePainter(R.drawable.loading)
    AsyncImage(
       model = categoryModel.imageUrl,
        placeholder = painter,
        contentDescription = "Categories",
        contentScale = ContentScale.Inside,
        modifier = Modifier.size(55.dp)
    )

}

data class ShoppingDescription(
    val dressImg: Int,
    val ProductName: String,
    val ProductModel: String,
    val ProductPrice: String,
    val ProductDiscountedPrice: String,
    val ProductDiscountPercentage: String,
)

@Composable
fun ShoppingList() {
    val dressDescription = listOf(
        ShoppingDescription(
            R.drawable.dress1,
            "One Shoulder Linen Dress",
            "GF1025",
            "Rs: 5740",
            "Rs: 7180",
            "20% off"
        ),
        ShoppingDescription(
            R.drawable.dress2,
            "Puff Sleeve Dress",
            "GF1047",
            "Rs: 5270",
            "Rs: 6200",
            "15% off"
        ),
        ShoppingDescription(
            R.drawable.dress3, "One Shoulder Linen Dress",
            "GF1025",
            "Rs: 5740",
            "Rs: 7180",
            "20% off"
        ),
        ShoppingDescription(
            R.drawable.dress4,
            "Puff Sleeve Dress",
            "GF1047",
            "Rs: 5270",
            "Rs: 6200",
            "15% off"
        )
    )
    LazyRow {
        items(dressDescription) {
            DressImage(it)
            Box(modifier = Modifier.padding(vertical = 20.dp))
        }
    }
}


@Composable
fun DressImage(dress: ShoppingDescription) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = Modifier
                .height(250.dp)
                .width(150.dp)
                .padding(vertical = 15.dp, horizontal = 5.dp), shape = RoundedCornerShape(25.dp)
        ) {
            Image(
                painter = painterResource(id = dress.dressImg),
                contentDescription = null,
                Modifier.fillMaxSize(), contentScale = ContentScale.Crop
            )
        }
        Card(
            modifier = Modifier
                .height(200.dp)
                .width(150.dp)
                .padding(vertical = 15.dp, horizontal = 5.dp),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(13.dp),
            border = BorderStroke(0.75.dp, Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp)
            ) {
                Text(
                    text = dress.ProductName,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    fontSize = 18.sp, textAlign = TextAlign.Center
                )
                Text(
                    text = dress.ProductModel,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                    fontSize = 15.sp
                )
                Text(
                    text = dress.ProductPrice,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    fontSize = 13.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dress.ProductDiscountedPrice,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                        fontSize = 15.sp
                    )
                    Text(
                        text = dress.ProductDiscountPercentage, fontSize = 15.sp, color = Pink80
                    )
                }
            }
        }
    }
}