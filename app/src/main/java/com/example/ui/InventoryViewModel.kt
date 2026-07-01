package com.example.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.Customer
import com.example.data.PerfumeRepository
import com.example.data.Product
import com.example.data.Purchase
import com.example.data.Sale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    // Database Initialization
    private val database: AppDatabase = Room.databaseBuilder(
        application.applicationContext,
        AppDatabase::class.java,
        "scentory_perfume_db"
    ).fallbackToDestructiveMigration().build()

    val repository = PerfumeRepository(database)

    // UI state inputs for filters
    val searchQuery = MutableStateFlow("")
    val categoryFilter = MutableStateFlow("All")
    val brandFilter = MutableStateFlow("All")
    val dateFilter = MutableStateFlow<Long?>(null) // Milliseconds of selected day

    // Authentication State
    val isAdminLoggedIn = MutableStateFlow(false)
    val configuredPassword = MutableStateFlow("1234") // Simple pin
    val showPinError = MutableStateFlow(false)

    // Dynamic sales target state
    val dailyTarget = MutableStateFlow(500.0) // Default $500 target

    // Local transient notifications
    val notificationMessages = MutableStateFlow<List<String>>(emptyList())

    // Base database flows
    val products: StateFlow<List<Product>> = repository.allProducts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val sales: StateFlow<List<Sale>> = repository.allSales.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val purchases: StateFlow<List<Purchase>> = repository.allPurchases.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val customers: StateFlow<List<Customer>> = repository.allCustomers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // Filtered Products flow
    val filteredProducts: StateFlow<List<Product>> = combine(
        products, searchQuery, categoryFilter, brandFilter
    ) { prodList, query, cat, brand ->
        prodList.filter { p ->
            val matchesQuery = p.name.contains(query, ignoreCase = true) || p.brand.contains(query, ignoreCase = true)
            val matchesCategory = cat == "All" || p.category.equals(cat, ignoreCase = true)
            val matchesBrand = brand == "All" || p.brand.equals(brand, ignoreCase = true)
            matchesQuery && matchesCategory && matchesBrand
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Sales Flow
    val filteredSales: StateFlow<List<Sale>> = combine(
        sales, searchQuery, categoryFilter, brandFilter, dateFilter
    ) { saleList, query, cat, brand, dateMs ->
        saleList.filter { s ->
            val matchesQuery = s.productName.contains(query, ignoreCase = true) || s.brand.contains(query, ignoreCase = true)
            val matchesCategory = cat == "All" || s.category.equals(cat, ignoreCase = true)
            val matchesBrand = brand == "All" || s.brand.equals(brand, ignoreCase = true)
            val matchesDate = if (dateMs == null) true else isSameDay(s.dateMillis, dateMs)
            matchesQuery && matchesCategory && matchesBrand && matchesDate
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Auto-calculates unique Brands and Categories for filtering lists
    val availableCategories: StateFlow<List<String>> = products.combine(sales) { prodList, saleList ->
        val cats = (prodList.map { it.category } + saleList.map { it.category }).filter { it.isNotBlank() }.distinct()
        listOf("All") + cats
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf("All"))

    val availableBrands: StateFlow<List<String>> = products.combine(sales) { prodList, saleList ->
        val brands = (prodList.map { it.brand } + saleList.map { it.brand }).filter { it.isNotBlank() }.distinct()
        listOf("All") + brands
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), listOf("All"))

    // --- Dashboard Calculations & Statistics ---
    val dashboardStats: StateFlow<DashboardStatistics> = combine(
        products, sales, dailyTarget
    ) { prodList, saleList, target ->
        val todayStart = getStartOfDayMillis()
        val monthStart = getStartOfMonthMillis()

        val todaySalesList = saleList.filter { s -> s.dateMillis >= todayStart }
        val monthSalesList = saleList.filter { s -> s.dateMillis >= monthStart }

        val todaySalesAmount = todaySalesList.sumOf { it.finalSellingPrice }
        val monthlySalesAmount = monthSalesList.sumOf { it.finalSellingPrice }

        val totalRevenue = saleList.sumOf { it.finalSellingPrice }
        val totalProfit = saleList.sumOf { it.profit }
        val totalProductsSold = saleList.sumOf { it.quantity }

        val currentInventoryValue = prodList.sumOf { it.stockValue }

        val lowStockProductsCount = prodList.count { it.isLowStock }
        val outOfStockProductsCount = prodList.count { it.isOutOfStock }

        // Top Selling Products (grouped by productId, summed by quantity)
        val topSelling = saleList.groupBy { it.productId }
            .map { (pid, pSales) ->
                val qty = pSales.sumOf { it.quantity }
                val rev = pSales.sumOf { it.finalSellingPrice }
                val name = pSales.firstOrNull()?.productName ?: "Unknown Product"
                TopProductInfo(productId = pid, name = name, totalQuantity = qty, totalRevenue = rev)
            }
            .sortedByDescending { it.totalQuantity }
            .take(5)

        // Generate Live Alerts
        val alerts = mutableListOf<String>()
        prodList.filter { it.isOutOfStock }.forEach {
            alerts.add("🚨 OUT OF STOCK: \"${it.name} (${it.size})\" requires immediate restocking.")
        }
        prodList.filter { it.isLowStock }.forEach {
            alerts.add("⚠️ LOW STOCK: \"${it.name} (${it.size})\" only has ${it.currentStock} units left.")
        }
        if (todaySalesAmount >= target) {
            alerts.add("🎉 TARGET ACHIEVED! Today's sales ($${String.format("%.2f", todaySalesAmount)}) reached the daily target of $${String.format("%.2f", target)}!")
        }

        // Expose alerts to notifications flow if changed
        updateNotifications(alerts)

        DashboardStatistics(
            todaySales = todaySalesAmount,
            monthlySales = monthlySalesAmount,
            totalRevenue = totalRevenue,
            totalProfit = totalProfit,
            totalProductsSold = totalProductsSold,
            inventoryValue = currentInventoryValue,
            lowStockCount = lowStockProductsCount,
            outOfStockCount = outOfStockProductsCount,
            topSellingProducts = topSelling,
            recentSales = saleList.take(8),
            activeAlerts = alerts
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DashboardStatistics())

    private fun updateNotifications(newAlerts: List<String>) {
        if (notificationMessages.value != newAlerts) {
            notificationMessages.value = newAlerts
        }
    }

    // --- Core Operations ---

    fun login(pin: String): Boolean {
        return if (pin == configuredPassword.value) {
            isAdminLoggedIn.value = true
            showPinError.value = false
            true
        } else {
            showPinError.value = true
            false
        }
    }

    fun logout() {
        isAdminLoggedIn.value = false
    }

    fun updatePin(newPin: String) {
        if (newPin.isNotBlank()) {
            configuredPassword.value = newPin
        }
    }

    fun addProduct(
        name: String,
        brand: String,
        category: String,
        size: String,
        buyPrice: Double,
        sellPrice: Double,
        initialStock: Int,
        notes: String = "",
        imageUri: String? = null,
        barcode: String? = null
    ) {
        viewModelScope.launch {
            val product = Product(
                name = name,
                brand = brand,
                category = category,
                size = size,
                buyPrice = buyPrice,
                sellPrice = sellPrice,
                currentStock = initialStock,
                notes = notes,
                imageUri = imageUri,
                barcode = barcode
            )
            repository.addProduct(product)
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    fun addSale(
        productId: Int,
        quantity: Int,
        finalSellingPrice: Double,
        dateMillis: Long,
        customerName: String?,
        customerPhone: String?,
        customerAddress: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.sellProductSuspend(
                    productId = productId,
                    quantity = quantity,
                    finalSellingPrice = finalSellingPrice,
                    dateMillis = dateMillis,
                    customerName = customerName,
                    customerPhone = customerPhone,
                    address = customerAddress
                )
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to record sale")
            }
        }
    }

    fun addPurchase(
        productId: Int,
        quantityPurchased: Int,
        buyPrice: Double,
        supplier: String,
        dateMillis: Long,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.purchaseProductSuspend(
                    productId = productId,
                    quantityPurchased = quantityPurchased,
                    buyPrice = buyPrice,
                    supplier = supplier,
                    dateMillis = dateMillis
                )
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Failed to record purchase")
            }
        }
    }

    fun addCustomerProfile(name: String, phone: String, address: String) {
        viewModelScope.launch {
            repository.addCustomer(Customer(phone = phone, name = name, address = address))
        }
    }

    fun deleteCustomerProfile(customer: Customer) {
        viewModelScope.launch {
            repository.deleteCustomer(customer)
        }
    }

    // --- Report Calculations ---
    fun generateReportData(type: ReportType): ReportData {
        val allS = sales.value
        val allP = products.value
        val now = System.currentTimeMillis()

        val filteredSalesList = when (type) {
            ReportType.DAILY -> allS.filter { isSameDay(it.dateMillis, now) }
            ReportType.WEEKLY -> allS.filter { isSameWeek(it.dateMillis, now) }
            ReportType.MONTHLY -> allS.filter { isSameMonth(it.dateMillis, now) }
            ReportType.YEARLY -> allS.filter { isSameYear(it.dateMillis, now) }
        }

        val totalRevenue = filteredSalesList.sumOf { it.finalSellingPrice }
        val totalCost = filteredSalesList.sumOf { it.buyPrice * it.quantity }
        val totalProfit = filteredSalesList.sumOf { it.profit }
        val totalQtySold = filteredSalesList.sumOf { it.quantity }

        // Top Selling Products in this report
        val topSellingInReport = filteredSalesList.groupBy { it.productId }
            .map { (pid, pSales) ->
                val qty = pSales.sumOf { it.quantity }
                val rev = pSales.sumOf { it.finalSellingPrice }
                val name = pSales.firstOrNull()?.productName ?: "Unknown"
                val prof = pSales.sumOf { it.profit }
                TopProductInfo(productId = pid, name = name, totalQuantity = qty, totalRevenue = rev, totalProfit = prof)
            }
            .sortedByDescending { it.totalQuantity }

        // Dead Stock (products with 0 sales overall)
        val soldProductIds = allS.map { it.productId }.toSet()
        val deadStock = allP.filter { it.id !in soldProductIds }

        return ReportData(
            reportType = type,
            salesCount = filteredSalesList.size,
            revenue = totalRevenue,
            profit = totalProfit,
            quantitySold = totalQtySold,
            topSelling = topSellingInReport,
            deadStock = deadStock,
            salesList = filteredSalesList
        )
    }

    // --- Sharing/Export Utilities ---
    fun exportReportToCSV(context: Context, type: ReportType) {
        val report = generateReportData(type)
        val csvBuilder = StringBuilder()

        csvBuilder.append("Scentory Perfume Inventory - ${type.name} Sales Report\n")
        csvBuilder.append("Generated on: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())}\n\n")

        csvBuilder.append("SUMMARY STATS\n")
        csvBuilder.append("Total Revenue,$${String.format("%.2f", report.revenue)}\n")
        csvBuilder.append("Total Net Profit,$${String.format("%.2f", report.profit)}\n")
        csvBuilder.append("Total Items Sold,${report.quantitySold}\n")
        csvBuilder.append("Total Sales Transactions,${report.salesCount}\n\n")

        csvBuilder.append("SALES HISTORY\n")
        csvBuilder.append("Date,Product Name,Brand,Size,Qty,Unit Cost,Total Sale Amount,Profit,Customer\n")

        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        report.salesList.forEach { s ->
            csvBuilder.append("\"${df.format(Date(s.dateMillis))}\",")
            csvBuilder.append("\"${s.productName}\",")
            csvBuilder.append("\"${s.brand}\",")
            csvBuilder.append("\"${s.size}\",")
            csvBuilder.append("${s.quantity},")
            csvBuilder.append("${s.buyPrice},")
            csvBuilder.append("${s.finalSellingPrice},")
            csvBuilder.append("${s.profit},")
            csvBuilder.append("\"${s.customerName ?: "Walk-in"}\"\n")
        }

        shareText(context, csvBuilder.toString(), "scentory_${type.name.lowercase()}_report.csv")
    }

    fun exportInventoryToCSV(context: Context) {
        val list = products.value
        val csvBuilder = StringBuilder()

        csvBuilder.append("Scentory Perfume Inventory - Complete Stock Valuation\n")
        csvBuilder.append("Generated on: ${SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())}\n\n")

        csvBuilder.append("Product ID,Product Name,Brand,Category,Size,Cost Price,Retail Price,Current Stock,Stock Value,Alert Status\n")
        list.forEach { p ->
            val alert = if (p.isOutOfStock) "OUT OF STOCK" else if (p.isLowStock) "LOW STOCK" else "OK"
            csvBuilder.append("${p.id},")
            csvBuilder.append("\"${p.name}\",")
            csvBuilder.append("\"${p.brand}\",")
            csvBuilder.append("\"${p.category}\",")
            csvBuilder.append("\"${p.size}\",")
            csvBuilder.append("${p.buyPrice},")
            csvBuilder.append("${p.sellPrice},")
            csvBuilder.append("${p.currentStock},")
            csvBuilder.append("${p.stockValue},")
            csvBuilder.append("\"$alert\"\n")
        }

        shareText(context, csvBuilder.toString(), "scentory_inventory_report.csv")
    }

    private fun shareText(context: Context, content: String, filename: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_SUBJECT, filename)
            putExtra(Intent.EXTRA_TEXT, content)
        }
        val chooser = Intent.createChooser(intent, "Share / Export Report")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    // --- Helper time checkers ---
    private fun isSameDay(m1: Long, m2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = m1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = m2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun isSameWeek(m1: Long, m2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = m1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = m2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)
    }

    private fun isSameMonth(m1: Long, m2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = m1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = m2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
    }

    private fun isSameYear(m1: Long, m2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = m1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = m2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
    }

    private fun getStartOfDayMillis(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }

    private fun getStartOfMonthMillis(): Long {
        return Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
}

// Data Classes for UI state representation
data class DashboardStatistics(
    val todaySales: Double = 0.0,
    val monthlySales: Double = 0.0,
    val totalRevenue: Double = 0.0,
    val totalProfit: Double = 0.0,
    val totalProductsSold: Int = 0,
    val inventoryValue: Double = 0.0,
    val lowStockCount: Int = 0,
    val outOfStockCount: Int = 0,
    val topSellingProducts: List<TopProductInfo> = emptyList(),
    val recentSales: List<Sale> = emptyList(),
    val activeAlerts: List<String> = emptyList()
)

data class TopProductInfo(
    val productId: Int,
    val name: String,
    val totalQuantity: Int,
    val totalRevenue: Double,
    val totalProfit: Double = 0.0
)

enum class ReportType {
    DAILY, WEEKLY, MONTHLY, YEARLY
}

data class ReportData(
    val reportType: ReportType,
    val salesCount: Int,
    val revenue: Double,
    val profit: Double,
    val quantitySold: Int,
    val topSelling: List<TopProductInfo>,
    val deadStock: List<Product>,
    val salesList: List<Sale>
)
