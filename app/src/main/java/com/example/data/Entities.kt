package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val brand: String,
    val category: String,
    val size: String,
    val buyPrice: Double,
    val sellPrice: Double,
    val currentStock: Int,
    val notes: String = "",
    val imageUri: String? = null,
    val barcode: String? = null
) {
    val stockValue: Double
        get() = currentStock * buyPrice

    val isLowStock: Boolean
        get() = currentStock in 1..4

    val isOutOfStock: Boolean
        get() = currentStock <= 0
}

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val productName: String,
    val brand: String = "",
    val category: String = "",
    val size: String = "",
    val quantity: Int,
    val buyPrice: Double,
    val finalSellingPrice: Double, // This is total sales amount for the given quantity
    val profit: Double, // Automatically calculated as finalSellingPrice - (buyPrice * quantity)
    val dateMillis: Long = System.currentTimeMillis(),
    val customerName: String? = null,
    val customerPhone: String? = null
)

@Entity(tableName = "purchases")
data class Purchase(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val productName: String,
    val brand: String = "",
    val size: String = "",
    val quantityPurchased: Int,
    val buyPrice: Double,
    val supplier: String,
    val dateMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "customers")
data class Customer(
    @PrimaryKey val phone: String, // Treat phone as unique key
    val name: String,
    val address: String = ""
)
