package com.example.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material.icons.filled.Star
import androidx.lifecycle.ViewModel
import com.example.models.CartItem
import com.example.models.Category
import com.example.models.Product
import com.example.models.SellerStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MarketplaceViewModel : ViewModel() {

    private val _isSellerMode = MutableStateFlow(false)
    val isSellerMode: StateFlow<Boolean> = _isSellerMode.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun toggleMode() {
        _isSellerMode.update { !it }
    }

    fun addToCart(product: Product) {
        _cartItems.update { items ->
            val existingItem = items.find { it.product.id == product.id }
            if (existingItem != null) {
                items.map { if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it }
            } else {
                items + CartItem(product, 1)
            }
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.update { items ->
            items.filter { it.product.id != productId }
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(productId)
            return
        }
        _cartItems.update { items ->
            items.map { if (it.product.id == productId) it.copy(quantity = quantity) else it }
        }
    }

    // Mock Data
    val categories = listOf(
        Category("1", "Electronics", Icons.Default.Smartphone),
        Category("2", "Fashion", Icons.Default.ShoppingBag),
        Category("3", "Home", Icons.Default.Home),
        Category("4", "Beauty", Icons.Default.Face),
        Category("5", "Sports", Icons.Default.Star)
    )

    val products = listOf(
        Product("1", "Wireless Noise Cancelling Headphones", 299.99, 349.99, 4.8, 1245, "https://picsum.photos/seed/p1/400", "s1", true),
        Product("2", "Men's Classic Cotton T-Shirt", 15.99, 25.00, 4.5, 850, "https://picsum.photos/seed/p2/400", "s2", false),
        Product("3", "Smart LED TV 55 Inch", 499.00, 599.00, 4.6, 320, "https://picsum.photos/seed/p3/400", "s3", true),
        Product("4", "Running Shoes for Men", 89.50, 110.00, 4.2, 540, "https://picsum.photos/seed/p4/400", "s1", false),
        Product("5", "Organic Face Serum 30ml", 24.99, null, 4.9, 210, "https://picsum.photos/seed/p5/400", "s2", false),
        Product("6", "Mechanical Gaming Keyboard", 129.99, 149.99, 4.7, 890, "https://picsum.photos/seed/p6/400", "s3", true)
    )

    val sellerStats = SellerStats(
        totalSales = 12450.50,
        pendingOrders = 14,
        productsCount = 45,
        rating = 4.8
    )

    val sellerProducts = products.filter { it.sellerId == "s1" }
}
