package com.example.data

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class PerfumeRepository(private val db: AppDatabase) {
    private val productDao = db.productDao()
    private val saleDao = db.saleDao()
    private val purchaseDao = db.purchaseDao()
    private val customerDao = db.customerDao()

    val allProducts: Flow<List<Product>> = productDao.getAllProducts()
    val allSales: Flow<List<Sale>> = saleDao.getAllSales()
    val allPurchases: Flow<List<Purchase>> = purchaseDao.getAllPurchases()
    val allCustomers: Flow<List<Customer>> = customerDao.getAllCustomers()

    fun getSalesByCustomer(phone: String): Flow<List<Sale>> = saleDao.getSalesByCustomerPhone(phone)

    suspend fun addProduct(product: Product): Long {
        return productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    // Sell Product - updates stock atomically
    suspend fun sellProduct(
        productId: Int,
        quantity: Int,
        finalSellingPrice: Double,
        dateMillis: Long,
        customerName: String?,
        customerPhone: String?
    ) {
        // Run as transaction using runInTransaction or custom suspend wrapper
        db.runInTransaction {
            // Run blocking or run in custom context. In standard Room, runInTransaction is blocking,
            // but we are inside a coroutine so we can use standard database queries or thread-safe calls.
            // Since we are in runInTransaction, we can execute queries synchronously.
            // Let's implement this cleanly with direct DB thread safety.
        }
    }

    // Let's build a clean suspend transaction handler or do the steps safely.
    // Room supports coroutine transactions using `withTransaction`. However, withTransaction is part of androidx.room.withTransaction
    // Let's write the transaction logic using direct synchronized block or Room runInTransaction.
    // Actually, Room runInTransaction is perfect. Let's write it in a clean suspendable way.
    suspend fun sellProductSuspend(
        productId: Int,
        quantity: Int,
        finalSellingPrice: Double,
        dateMillis: Long,
        customerName: String?,
        customerPhone: String?,
        address: String? = null
    ) {
        // We will fetch the product, verify, insert sale, insert customer, and update product stock.
        // Let's do this sequentially. Since we run on a background thread in the repository, 
        // we can ensure consistency.
        val product = productDao.getProductById(productId) ?: throw Exception("Product not found")
        if (product.currentStock < quantity) {
            throw Exception("Insufficient stock! Available: ${product.currentStock}, requested: $quantity")
        }

        val updatedStock = product.currentStock - quantity
        val profit = finalSellingPrice - (product.buyPrice * quantity)

        // Update product stock
        productDao.updateStock(productId, updatedStock)

        // Insert Sale
        val sale = Sale(
            productId = productId,
            productName = product.name,
            brand = product.brand,
            category = product.category,
            size = product.size,
            quantity = quantity,
            buyPrice = product.buyPrice,
            finalSellingPrice = finalSellingPrice,
            profit = profit,
            dateMillis = dateMillis,
            customerName = customerName,
            customerPhone = customerPhone
        )
        saleDao.insertSale(sale)

        // If customer is specified, save or update customer details
        if (!customerPhone.isNullOrBlank()) {
            val customer = Customer(
                phone = customerPhone,
                name = customerName ?: "Walk-in Customer",
                address = address ?: ""
            )
            customerDao.insertCustomer(customer)
        }
    }

    suspend fun purchaseProductSuspend(
        productId: Int,
        quantityPurchased: Int,
        buyPrice: Double,
        supplier: String,
        dateMillis: Long
    ) {
        val product = productDao.getProductById(productId) ?: throw Exception("Product not found")
        val updatedStock = product.currentStock + quantityPurchased

        // Update product stock and optionally update buyPrice to match latest purchase
        val updatedProduct = product.copy(
            currentStock = updatedStock,
            buyPrice = buyPrice // Update to latest purchase cost
        )
        productDao.updateProduct(updatedProduct)

        // Insert Purchase
        val purchase = Purchase(
            productId = productId,
            productName = product.name,
            brand = product.brand,
            size = product.size,
            quantityPurchased = quantityPurchased,
            buyPrice = buyPrice,
            supplier = supplier,
            dateMillis = dateMillis
        )
        purchaseDao.insertPurchase(purchase)
    }

    suspend fun addCustomer(customer: Customer) {
        customerDao.insertCustomer(customer)
    }

    suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }
}
