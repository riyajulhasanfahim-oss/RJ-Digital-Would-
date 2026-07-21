package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.MarketplaceBottomBar
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.buyer.CartScreen
import com.example.ui.screens.buyer.HomeScreen
import com.example.ui.screens.seller.SellerDashboardScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodels.MarketplaceViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        MarketplaceApp()
      }
    }
  }
}

@Composable
fun MarketplaceApp() {
    val viewModel: MarketplaceViewModel = viewModel()
    val navController = rememberNavController()
    val isSellerMode by viewModel.isSellerMode.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "home"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MarketplaceBottomBar(
                currentRoute = currentRoute,
                isSellerMode = isSellerMode,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Buyer routes
            composable("home") {
                HomeScreen(
                    categories = viewModel.categories,
                    products = viewModel.products,
                    onProductClick = {
                        val product = viewModel.products.find { p -> p.id == it }
                        if (product != null) {
                            viewModel.addToCart(product)
                        }
                    }
                )
            }
            composable("cart") {
                CartScreen(
                    cartItems = cartItems,
                    onUpdateQuantity = viewModel::updateQuantity,
                    onCheckout = { /* TODO */ }
                )
            }
            
            // Common routes
            composable("profile") {
                ProfileScreen(
                    isSellerMode = isSellerMode,
                    onToggleMode = {
                        viewModel.toggleMode()
                        val newRoute = if (!isSellerMode) "dashboard" else "home"
                        navController.navigate(newRoute) {
                            popUpTo(0) // Clear back stack on mode switch
                        }
                    }
                )
            }

            // Seller routes
            composable("dashboard") {
                SellerDashboardScreen(
                    stats = viewModel.sellerStats,
                    recentProducts = viewModel.sellerProducts
                )
            }
            composable("products") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Products Management Screen")
                }
            }
            composable("orders") {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Orders Management Screen")
                }
            }
        }
    }
}

