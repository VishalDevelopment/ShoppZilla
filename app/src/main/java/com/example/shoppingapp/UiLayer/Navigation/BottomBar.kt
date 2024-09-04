package com.example.shoppingapp.UiLayer.Navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shoppingapp.UiLayer.Screens.Cart_Screen.CartScreen
import com.example.shoppingapp.UiLayer.Screens.Category_Screen.CategoryScreen
import com.example.shoppingapp.UiLayer.Screens.HomeScreen
import com.example.shoppingapp.UiLayer.Screens.ProductScreen
import com.example.shoppingapp.UiLayer.Screens.Profile_Screen.ProfileScreen
import com.example.shoppingapp.UiLayer.Screens.Wishlist_Screen.WishlistScreen
import com.example.shoppingapp.ui.theme.LitePink
import com.example.shoppingapp.ui.theme.Pink80
import com.google.firebase.auth.FirebaseAuth


data class BottomItem(
    val title: String,
    val selectedImg: ImageVector,
    val unselectedImg: ImageVector,
    var navigate : Any
)

@Composable

fun BottomBar(firebaseAuth: FirebaseAuth,MainNavHost:NavHostController) {

    val navController = rememberNavController()

    val items = listOf(
        BottomItem("Home", Icons.Default.Home, Icons.Outlined.Home,Routes.Home),
        BottomItem("Wishlist", Icons.Default.FavoriteBorder, Icons.Outlined.FavoriteBorder,Routes.Wishlist),
        BottomItem("Cart", Icons.Default.ShoppingCart, Icons.Outlined.ShoppingCart,Routes.Cart),
        BottomItem("Profile", Icons.Default.AccountCircle, Icons.Outlined.AccountCircle,Routes.Profile)
    )

    var selectedItem by remember {
        mutableStateOf(
            BottomItem("Home", Icons.Default.Home, Icons.Outlined.Home,Routes.Home),
        )
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Pink80,
                modifier = Modifier.height(50.dp),
                windowInsets = WindowInsets(0.dp)
            ) {
                items.forEach { index ->
                    NavigationBarItem(
                        selected = index == selectedItem,
                        onClick = {
                            selectedItem = index
                         navController.navigate(selectedItem.navigate){
                             // Clear back stack for login/signup flow

                         }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selectedItem == index) {
                                    index.selectedImg
                                } else {
                                    index.unselectedImg
                                }, contentDescription = null
                            )
                        }, colors = NavigationBarItemColors(Color.Black,Color.Black,
                            LitePink,Color.Black,Color.Black,Color.Black,Color.Black)
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = selectedItem.navigate) {
                composable<Routes.Home> {
                    HomeScreen(navController)
                }
                composable<Routes.Wishlist> {
                    WishlistScreen(firebaseAuth)
                }
                composable<Routes.Cart> {
                    CartScreen(firebaseAuth)
                }
                composable<Routes.Profile> {
                    ProfileScreen(firebaseAuth = firebaseAuth){
                            MainNavHost.navigate(Routes.Auth) {
                                popUpTo(route = Routes.Main) {
                                    inclusive = true
                                }
                            }
                        }
                }

                composable<Routes.ProductDetail>{
                    val  productId = it.toRoute<Routes.ProductDetail>()
                    ProductScreen(productId.productId, firebaseAuth)
                }

                composable<Routes.Category> {
                    val CategoryName = it.toRoute<Routes.Category>()
                    CategoryScreen(CategoryName.categoryName)
                }
            }
        }
    }
}