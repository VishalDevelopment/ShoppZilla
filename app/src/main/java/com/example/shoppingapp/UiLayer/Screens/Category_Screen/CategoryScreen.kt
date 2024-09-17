package com.example.shoppingapp.UiLayer.Screens.Category_Screen

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shoppingapp.DomainLayer.Model.ProductModel
import com.example.shoppingapp.UiLayer.Navigation.Routes
import com.example.shoppingapp.ui.theme.Pink80

@Composable

fun CategoryScreen(categoryName: String, navController: NavHostController) {
    val CATEGORYVM: CategoryVm = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        CATEGORYVM.filterCategory(categoryName)
    }
    val filterCategoryList = CATEGORYVM.filterCategory.collectAsState()
    var FilterCategoryData = remember {
        mutableStateOf(emptyList<ProductModel>())
    }
    when (filterCategoryList.value) {
        is FilterCategoryState.Error -> {

        }

        FilterCategoryState.Loading -> {
            Box(contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        }

        is FilterCategoryState.Success -> {
            val filterData =
                (filterCategoryList.value as FilterCategoryState.Success).filterCategory
            FilterCategoryData.value = filterData


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    Text(
                        text = "$categoryName",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 32.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.SemiBold
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(FilterCategoryData.value) {
                            CategoryGrid(it, navController)
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun CategoryGrid(productModel: ProductModel, navController: NavHostController) {

    Card(
        modifier = Modifier
            .height(310.dp)
            .width(150.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                navController.navigate(Routes.ProductDetail(productModel.id))
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(Color.Transparent),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .fillMaxHeight(.55f)
                    .padding(top = 10.dp)
            ) {
                AsyncImage(
                    model = productModel.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${productModel.name}",
                fontSize = 12.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp), horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Rs ${productModel.discountedPrice}",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    fontSize = 15.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Rs ${productModel.actualPrice}",
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 3.dp),
                        fontSize = 15.sp
                    )
                    Text(
                        text = "-  ${productModel.discount}%", fontSize = 15.sp, color = Pink80
                    )
                }
            }
        }
    }
}


