package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.border
import androidx.compose.ui.unit.dp

@Composable
fun MarketplaceBottomBar(
    currentRoute: String,
    isSellerMode: Boolean,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        modifier = Modifier.border(width = 1.dp, color = MaterialTheme.colorScheme.surfaceVariant, shape = androidx.compose.ui.graphics.RectangleShape)
    ) {
        if (isSellerMode) {
            NavigationBarItem(
                selected = currentRoute == "dashboard",
                onClick = { onNavigate("dashboard") },
                icon = { Icon(Icons.Default.Dashboard, contentDescription = "Dashboard") },
                label = { Text("Dashboard") }
            )
            NavigationBarItem(
                selected = currentRoute == "products",
                onClick = { onNavigate("products") },
                icon = { Icon(Icons.Default.Inventory, contentDescription = "Products") },
                label = { Text("Products") }
            )
            NavigationBarItem(
                selected = currentRoute == "orders",
                onClick = { onNavigate("orders") },
                icon = { Icon(Icons.AutoMirrored.Filled.ListAlt, contentDescription = "Orders") },
                label = { Text("Orders") }
            )
            NavigationBarItem(
                selected = currentRoute == "profile",
                onClick = { onNavigate("profile") },
                icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                label = { Text("Profile") }
            )
        } else {
            NavigationBarItem(
                selected = currentRoute == "home",
                onClick = { onNavigate("home") },
                icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                label = { Text("Home") }
            )
            NavigationBarItem(
                selected = currentRoute == "cart",
                onClick = { onNavigate("cart") },
                icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                label = { Text("Cart") }
            )
            NavigationBarItem(
                selected = currentRoute == "profile",
                onClick = { onNavigate("profile") },
                icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
                label = { Text("Profile") }
            )
        }
    }
}
