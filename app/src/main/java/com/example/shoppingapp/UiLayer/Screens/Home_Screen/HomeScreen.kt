package com.example.shoppingapp.UiLayer.Screens.Home_Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.shoppingapp.DomainLayer.Model.CategoryModel
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.R
import com.example.shoppingapp.UiLayer.Navigation.Routes
import com.example.shoppingapp.UiLayer.ViewModel.CategoryState
import com.example.shoppingapp.UiLayer.ViewModel.ProductState
import com.example.shoppingapp.UiLayer.ViewModel.ShoppingVm
import com.example.shoppingapp.ui.theme.Pink80

@Composable
fun HomeScreen(navController: NavHostController) {
    val categoryData = remember { mutableStateOf(emptyList<CategoryModel>()) }
    var productData = remember {
        mutableStateOf(emptyList<ProductModel>())
    }
    val viewmodel: ShoppingVm = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        viewmodel.getCategory()
        viewmodel.getProduct()
    }
    val categorystate = viewmodel.CategoriesData.collectAsState()
    val productState = viewmodel.productList.collectAsState()
    when (categorystate.value) {
        is CategoryState.Error -> {
        }

        CategoryState.Loading -> {
            Home_Shimmer()
        }

        is CategoryState.Succes -> {
            val categoryList =
                (categorystate.value as CategoryState.Succes).category// Access the list
            categoryData.value = categoryList

            Box(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    SearchBar(navController)
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    val context = LocalContext.current
                    OptionHead("Categories", "See More") {
                        Toast.makeText(context, "Category-See More", Toast.LENGTH_SHORT).show()
                    }
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    Category(categoryData, navController)
                    Spacer(modifier = Modifier.padding(vertical = 5.dp))
                    OptionHead(title = "Flash Sale", moreData = "See More"){
                        navController.navigate(Routes.SearchBar)
                    }
                    ShoppingList(productData.value, navController)
                }

            }
        }
    }
    when (productState.value) {
        is ProductState.Error -> {
        }

        ProductState.Loading -> {
        }

        is ProductState.Success -> {
            val productList = (productState.value as ProductState.Success).product
            productData.value = productList
        }
    }


}

@Composable
fun OptionHead(title: String, moreData: String, MoveToSearchScreen: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                MoveToSearchScreen()
            },
        horizontalArrangement = Arrangement.SpaceBetween,

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
fun SearchBar(navController: NavHostController) {
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
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                placeholder = { Text(text = "Search") },
                modifier = Modifier
                    .fillMaxWidth(.90f)
                    .clickable {
                        navController.navigate(Routes.SearchBar)
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Pink80,
                    unfocusedBorderColor = Pink80
                ),
                shape = RoundedCornerShape(12.dp), readOnly = true, enabled = false
            )

        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = null,
            modifier = Modifier.size(35.dp)
        )
    }
}

@Composable
fun Category(categoryData: MutableState<List<CategoryModel>>, navController: NavHostController) {

    val interactivesource = remember {
        MutableInteractionSource()
    }
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
                    )
                    .clickable(
                        interactionSource = interactivesource,
                        indication = null
                    ) {
                        val categoryName = it.name
                        navController.navigate(Routes.Category(categoryName))
                    },
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


    Log.d("CATEGORYITEM", "${categoryModel.imageUrl}")
    val painter = rememberAsyncImagePainter(R.drawable.loading)
    val error = rememberAsyncImagePainter(model = R.drawable.process_error)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(categoryModel.imageUrl)
            .setHeader("User-Agent", "Mozilla/5.0")
            .build(),
        placeholder = painter,
        error = error,
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
fun ShoppingList(product: List<ProductModel>, navController: NavHostController) {

    val dressDescription = product
    LazyRow {
        items(dressDescription) {
            DressImage(it, navController)
            Box(modifier = Modifier.padding(vertical = 20.dp))
        }
    }
}


@Composable
fun DressImage(dress: ProductModel, navController: NavHostController) {

    val painter = rememberAsyncImagePainter(R.drawable.loading)
    val error = rememberAsyncImagePainter(model = R.drawable.process_error)
    Log.d("IMAGEPRODUCT", "${dress.imageUrl}")
    val Context = LocalContext.current



    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable {
        // Navigate to Product Screen
        navController.navigate(Routes.ProductDetail(dress.id))

    }) {
        Card(
            modifier = Modifier
                .height(250.dp)
                .width(150.dp)
                .padding(vertical = 15.dp, horizontal = 5.dp), shape = RoundedCornerShape(25.dp)
        ) {

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(dress.imageUrl)
                    .setHeader("User-Agent", "Mozilla/5.0")
                    .build(),
                contentDescription = null,
                Modifier.fillMaxSize(), contentScale = ContentScale.Crop,
                placeholder = painter,
                error = error
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
                    text = dress.name,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth(),
                    fontSize = 12.sp, textAlign = TextAlign.Center
                )
                Text(
                    text = "Test",
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                    fontSize = 10.sp
                )
                Text(
                    text = "Rs ${dress.discountedPrice}",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    fontSize = 10.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rs ${dress.actualPrice.toString()}",
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                        fontSize = 15.sp
                    )
                    Text(
                        text = "${dress.discount}%", fontSize = 15.sp, color = Pink80
                    )
                }
            }
        }
    }
}
