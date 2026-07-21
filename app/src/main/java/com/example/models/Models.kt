package com.example.models

data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val originalPrice: Double? = null,
    val rating: Double,
    val reviewsCount: Int,
    val imageUrl: String,
    val sellerId: String,
    val isFlashSale: Boolean = false
)

data class Category(
    val id: String,
    val name: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

data class CartItem(
    val product: Product,
    val quantity: Int
)

data class SellerStats(
    val totalSales: Double,
    val pendingOrders: Int,
    val productsCount: Int,
    val rating: Double
)
