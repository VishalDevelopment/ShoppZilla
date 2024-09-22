package com.example.shoppingapp.UiLayer.Navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shoppingapp.UiLayer.Screens.Cart_Screen.CartScreen
import com.example.shoppingapp.UiLayer.Screens.Category_Screen.CategoryScreen
import com.example.shoppingapp.UiLayer.Screens.Home_Screen.HomeScreen
import com.example.shoppingapp.UiLayer.Screens.Home_Screen.ProductScreen
import com.example.shoppingapp.UiLayer.Screens.Profile_Screen.ProfileScreen
import com.example.shoppingapp.UiLayer.Screens.PurchaseFinishScreen
import com.example.shoppingapp.UiLayer.Screens.Search_Screen.SearchScreen
import com.example.shoppingapp.UiLayer.Screens.Shipping_Screen.ShippingScreen
import com.example.shoppingapp.UiLayer.Screens.Shipping_Screen.ShippingViewModel
import com.example.shoppingapp.UiLayer.Screens.Wishlist_Screen.WishlistScreen
import com.example.shoppingapp.ui.theme.LitePink
import com.example.shoppingapp.ui.theme.Pink80
import com.google.firebase.auth.FirebaseAuth


data class BottomItem(
    val title: String,
    val selectedImg: ImageVector,
    val unselectedImg: ImageVector,
    var navigate: Any,
)

@Composable

fun BottomBar(firebaseAuth: FirebaseAuth, MainNavHost: NavHostController) {
    val navController = rememberNavController()
    val ShippingVm: ShippingViewModel = hiltViewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val items = listOf(
        BottomItem("Home", Icons.Default.Home, Icons.Outlined.Home, Routes.Home),
        BottomItem(
            "Wishlist",
            Icons.Default.Favorite,
            Icons.Outlined.FavoriteBorder,
            Routes.Wishlist
        ),
        BottomItem("Cart", Icons.Default.ShoppingCart, Icons.Outlined.ShoppingCart, Routes.Cart),
        BottomItem(
            "Profile",
            Icons.Default.AccountCircle,
            Icons.Outlined.AccountCircle,
            Routes.Profile
        )
    )
    var isVisible = remember {
        mutableStateOf(true)
    }
    val animationSpec = tween<IntSize>(durationMillis = 300, easing = LinearEasing)


    var selectedItem by remember {
        mutableStateOf(
            BottomItem("Home", Icons.Default.Home, Icons.Outlined.Home, Routes.Home),
        )
    }
    Scaffold(
            bottomBar = {
                if (isVisible.value){
                BottomAppBar(
                    containerColor = Pink80,
                    modifier = Modifier
                        .height(50.dp)
                        .animateContentSize(animationSpec)
                    ,
                    windowInsets = WindowInsets(0.dp),
                ) {
                    items.forEachIndexed { index, barItem ->
                        val selected =
                            currentDestination?.hierarchy?.any() { it.route == barItem.navigate::class.qualifiedName } == true
                        NavigationBarItem(
                            selected = barItem == selectedItem,
                            onClick = {
                                selectedItem = barItem

                            },
                            icon = {
                                Icon(
                                    imageVector = if (selectedItem == barItem) {
                                        barItem.selectedImg
                                    } else {
                                        barItem.unselectedImg
                                    }, contentDescription = null
                                )
                            }, colors = NavigationBarItemColors(
                                Color.Black, Color.Black,
                                LitePink, Color.Black, Color.Black, Color.Black, Color.Black
                            )
                        )
                    }
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = selectedItem.navigate) {
                composable<Routes.Home> {
                    isVisible.value = true
                    HomeScreen(navController)
                }
                composable<Routes.Wishlist> {
                    isVisible.value = true

                    WishlistScreen(firebaseAuth){
                        selectedItem = BottomItem("Home", Icons.Default.Home, Icons.Outlined.Home, Routes.Home)
                        }
                }
                composable<Routes.Cart> {
                    isVisible.value = true

                    CartScreen(firebaseAuth, navController, ShippingVm){
                        selectedItem = BottomItem("Home", Icons.Default.Home, Icons.Outlined.Home, Routes.Home)
                    }
                }
                composable<Routes.Profile> {
                    isVisible.value = true

                    ProfileScreen(firebaseAuth = firebaseAuth, {
                        MainNavHost.navigate(Routes.Auth,) {
                            popUpTo(route = Routes.Main) {
                                inclusive = true
                            }
                        }
                    },{
                        selectedItem = BottomItem("Home", Icons.Default.Home, Icons.Outlined.Home, Routes.Home)
                    })
                }

                composable<Routes.ProductDetail> (
                    enterTransition = ::slideInAnim,
                    exitTransition = ::slideOutAnim){
                    isVisible.value = false
                    val productId = it.toRoute<Routes.ProductDetail>()
                    ProductScreen(productId.productId, firebaseAuth, navController, ShippingVm)
                }

                composable<Routes.Category>(enterTransition = ::slideInAnim,
                    exitTransition = ::slideOutAnim) {
                    isVisible.value = false

                    val CategoryName = it.toRoute<Routes.Category>()
                    CategoryScreen(CategoryName.categoryName, navController)
                }

                composable<Routes.Shipping>(
                    enterTransition = ::slideInAnim,
                    exitTransition = ::slideOutAnim) {
                    isVisible.value = false

                    val productList = it.toRoute<Routes.Shipping>()
                    ShippingScreen(productList.Flag, ShippingVm, firebaseAuth, navController)
                }
                composable<Routes.Checkout>(
                    enterTransition = ::slideInAnim,
                    exitTransition = ::slideOutAnim
                ) {
                    isVisible.value = false
                    PurchaseFinishScreen() {
                        navController.navigate(Routes.Home) {
                            popUpTo(route = Routes.Checkout) {
                                inclusive = true
                            }
                        }
                    }
                }

                composable<Routes.SearchBar>{
                    SearchScreen()
                }
            }
        }
    }
}

fun slideInAnim(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
        animationSpec = tween(300)
    )
}

fun slideOutAnim(scope :AnimatedContentTransitionScope<NavBackStackEntry>):ExitTransition{
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
        animationSpec = tween(300)
    )
}