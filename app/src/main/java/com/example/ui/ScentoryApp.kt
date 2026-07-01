package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.ui.graphics.Brush
import com.example.data.Product
import com.example.data.Sale
import com.example.data.Purchase
import com.example.data.Customer
import com.example.ui.theme.WarningRed
import com.example.ui.theme.AmberAccent
import com.example.ui.theme.MintGreen
import com.example.ui.theme.DeepTeal
import com.example.ui.theme.LightMint

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BusinessCenter
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun glassBackgroundColor(): Color {
    return if (isSystemInDarkTheme()) {
        Color(0xFF1E293B).copy(alpha = 0.72f) // Semi-transparent dark slate
    } else {
        Color.White.copy(alpha = 0.82f) // Semi-transparent white
    }
}

@Composable
fun glassBorderColor(): Color {
    return if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.12f)
    } else {
        Color.White.copy(alpha = 0.7f)
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: androidx.compose.foundation.shape.CornerSize = androidx.compose.foundation.shape.CornerSize(32.dp),
    borderColor: Color = glassBorderColor(),
    backgroundColor: Color = glassBackgroundColor(),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardModifier = if (onClick != null) {
        modifier.clickable(onClick = onClick)
    } else {
        modifier
    }
    Card(
        modifier = cardModifier,
        shape = RoundedCornerShape(shape),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        border = BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            content()
        }
    }
}

@Composable
fun GlassThemeContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = isSystemInDarkTheme()
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(if (isDark) Color(0xFF0F172A) else Color(0xFFF1F5F9))
    ) {
        // Soft glowing aesthetic background circles/orbs
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Teal Glowing Orb
            drawCircle(
                color = if (isDark) Color(0xFF0D9488).copy(alpha = 0.12f) else Color(0xFF0D9488).copy(alpha = 0.16f),
                radius = 300.dp.toPx(),
                center = Offset(size.width * 0.15f, size.height * 0.2f)
            )
            // Mint/Emerald Glowing Orb
            drawCircle(
                color = if (isDark) Color(0xFF14B8A6).copy(alpha = 0.08f) else Color(0xFF14B8A6).copy(alpha = 0.12f),
                radius = 350.dp.toPx(),
                center = Offset(size.width * 0.85f, size.height * 0.45f)
            )
            // Amber Glowing Orb
            drawCircle(
                color = if (isDark) Color(0xFFF59E0B).copy(alpha = 0.06f) else Color(0xFFF59E0B).copy(alpha = 0.1f),
                radius = 280.dp.toPx(),
                center = Offset(size.width * 0.25f, size.height * 0.75f)
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScentoryApp(viewModel: InventoryViewModel) {
    val isAdminLoggedIn by viewModel.isAdminLoggedIn.collectAsState()
    val notificationMessages by viewModel.notificationMessages.collectAsState()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        TabItem("Dashboard", Icons.Filled.Dashboard),
        TabItem("Inventory", Icons.Filled.BusinessCenter),
        TabItem("Sales", Icons.Filled.ShoppingBag),
        TabItem("Purchases", Icons.Filled.TrendingUp),
        TabItem("Customers", Icons.Filled.People),
        TabItem("Reports", Icons.Filled.Analytics)
    )

    if (!isAdminLoggedIn) {
        AdminLoginScreen(viewModel)
    } else {
        BoxWithConstraints {
            val isTablet = maxWidth > 600.dp

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Category,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(28.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "SCENTORY",
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 1.5.sp,
                                    fontFamily = FontFamily.SansSerif
                                )
                            }
                        },
                        actions = {
                            var showNotifications by remember { mutableStateOf(false) }
                            Box {
                                IconButton(onClick = { showNotifications = !showNotifications }) {
                                    Icon(Icons.Filled.Notifications, contentDescription = "Alerts")
                                    if (notificationMessages.isNotEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .background(Color.Red, CircleShape)
                                                .align(Alignment.TopEnd)
                                                .padding(2.dp)
                                        )
                                    }
                                }

                                DropdownMenu(
                                    expanded = showNotifications,
                                    onDismissRequest = { showNotifications = false },
                                    modifier = Modifier.widthIn(max = 280.dp)
                                ) {
                                    if (notificationMessages.isEmpty()) {
                                        DropdownMenuItem(
                                            text = { Text("No active alerts. Stock levels optimal!") },
                                            onClick = { showNotifications = false }
                                        )
                                    } else {
                                        notificationMessages.forEach { msg ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = msg,
                                                        fontSize = 12.sp,
                                                        lineHeight = 16.sp
                                                    )
                                                },
                                                onClick = { showNotifications = false }
                                            )
                                            HorizontalDivider()
                                        }
                                    }
                                }
                            }

                            IconButton(
                                onClick = { viewModel.logout() },
                                modifier = Modifier.testTag("logout_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Logout,
                                    contentDescription = "Lock Dashboard"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                },
                bottomBar = {
                    if (!isTablet) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface,
                            tonalElevation = 8.dp
                        ) {
                            tabs.forEachIndexed { index, tab ->
                                NavigationBarItem(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    icon = { Icon(tab.icon, contentDescription = tab.title) },
                                    label = { Text(tab.title, fontSize = 11.sp) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                    )
                                )
                            }
                        }
                    }
                }
            ) { paddingValues ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    if (isTablet) {
                        NavigationRail(
                            containerColor = MaterialTheme.colorScheme.surface,
                            header = {
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        ) {
                            tabs.forEachIndexed { index, tab ->
                                NavigationRailItem(
                                    selected = selectedTab == index,
                                    onClick = { selectedTab = index },
                                    icon = { Icon(tab.icon, contentDescription = tab.title) },
                                    label = { Text(tab.title, fontSize = 12.sp) },
                                    colors = NavigationRailItemDefaults.colors(
                                        selectedIconColor = MaterialTheme.colorScheme.primary,
                                        selectedTextColor = MaterialTheme.colorScheme.primary,
                                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                    )
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                    }

                    GlassThemeContainer(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        when (selectedTab) {
                            0 -> DashboardScreen(viewModel)
                            1 -> InventoryScreen(viewModel)
                            2 -> SalesScreen(viewModel)
                            3 -> PurchasesScreen(viewModel)
                            4 -> CustomersScreen(viewModel)
                            5 -> ReportsScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}

data class TabItem(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

// --- ADMIN LOGIN SCREEN ---
@Composable
fun AdminLoginScreen(viewModel: InventoryViewModel) {
    var pin by remember { mutableStateOf("") }
    val hasError by viewModel.showPinError.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Secured",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SCENTORY SECURE PORTAL",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.sp
            )

            Text(
                text = "Enter security passcode to unlock your dashboard and sales engine.",
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            Card(
                modifier = Modifier
                    .widthIn(max = 400.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = pin,
                        onValueChange = { if (it.length <= 8) pin = it },
                        label = { Text("Security PIN") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        isError = hasError,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pin_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )

                    if (hasError) {
                        Text(
                            text = "Invalid passcode. Please try again.",
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(top = 4.dp, start = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hint: Default evaluation PIN is \"1234\"",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.login(pin) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("login_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("UNLOCK DASHBOARD", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }
        }
    }
}

// --- DASHBOARD SCREEN ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DashboardScreen(viewModel: InventoryViewModel) {
    val stats by viewModel.dashboardStats.collectAsState()
    val dailyTargetValue by viewModel.dailyTarget.collectAsState()

    var showTargetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Welcoming header with dynamic date
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "PARFUMIER PRO",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "Dashboard",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    letterSpacing = (-0.5).sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            OutlinedButton(
                onClick = { showTargetDialog = true },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Filled.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Set Daily Target", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Progress bar for Daily sales target
        val percent = if (dailyTargetValue > 0) (stats.todaySales / dailyTargetValue).coerceIn(0.0, 1.0) else 0.0
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF0D9488), Color(0xFF0F766E))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "Today's Revenue",
                                color = Color.White.copy(alpha = 0.82f),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "$${String.format("%.2f", stats.todaySales)}",
                                color = Color.White,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(100))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "${String.format("%.0f%%", percent * 100)} of Target",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Track bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.25f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(percent.toFloat())
                                .fillMaxHeight()
                                .background(Color.White)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Daily Target Limit",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "$${String.format("%.0f", dailyTargetValue)}",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Perfumes Sold",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 10.sp,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "${stats.totalProductsSold} units",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Grid of KPIs
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = 4,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val kpis = listOf(
                KpiData("Today's Sales", "$${String.format("%.2f", stats.todaySales)}", Icons.Filled.ShoppingBag, MaterialTheme.colorScheme.primary),
                KpiData("Monthly Sales", "$${String.format("%.2f", stats.monthlySales)}", Icons.Filled.TrendingUp, MaterialTheme.colorScheme.primary),
                KpiData("Total Revenue", "$${String.format("%.2f", stats.totalRevenue)}", Icons.Filled.BusinessCenter, MaterialTheme.colorScheme.primary),
                KpiData("Total Profit", "$${String.format("%.2f", stats.totalProfit)}", Icons.Filled.Analytics, Color(0xFF10B981)),
                KpiData("Inventory Value", "$${String.format("%.2f", stats.inventoryValue)}", Icons.Filled.Category, MaterialTheme.colorScheme.secondary),
                KpiData("Low Stock Items", "${stats.lowStockCount}", Icons.Filled.Warning, WarningRed),
                KpiData("Out of Stock", "${stats.outOfStockCount}", Icons.Filled.Close, WarningRed),
                KpiData("Perfumes Sold", "${stats.totalProductsSold} units", Icons.Filled.CheckCircle, MaterialTheme.colorScheme.tertiary)
            )

            kpis.forEach { kpi ->
                GlassCard(
                    modifier = Modifier
                        .weight(1f)
                        .widthIn(min = 140.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = kpi.title,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            fontWeight = FontWeight.Medium
                        )
                        Icon(
                            imageVector = kpi.icon,
                            contentDescription = null,
                            tint = kpi.color,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = kpi.value,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Visual Sales Canvas Chart
        Text(
            text = "Sales Performance (Monthly Share)",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomSalesChart(revenue = stats.totalRevenue, profit = stats.totalProfit, inventory = stats.inventoryValue)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Horizontal Row for Top Selling and Recent Sales
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Selling Products Card
            GlassCard(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 300.dp)
            ) {
                Text(
                    text = "Top Selling Perfumes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                    Spacer(modifier = Modifier.height(12.dp))
                    if (stats.topSellingProducts.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No sales registered yet",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    } else {
                        val maxQty = stats.topSellingProducts.maxOfOrNull { it.totalQuantity } ?: 1
                        stats.topSellingProducts.forEach { item ->
                            val scale = item.totalQuantity.toFloat() / maxQty.toFloat()
                            Column(modifier = Modifier.padding(vertical = 6.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = item.name,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = "${item.totalQuantity} sold",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.outlineVariant)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(scale)
                                            .fillMaxHeight()
                                            .background(MaterialTheme.colorScheme.primary)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Recent Transactions Card
            GlassCard(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 300.dp)
            ) {
                Text(
                    text = "Recent Transactions",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                    Spacer(modifier = Modifier.height(12.dp))
                    if (stats.recentSales.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No recent transactions",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    } else {
                        LazyColumn(modifier = Modifier.heightIn(max = 240.dp)) {
                            items(stats.recentSales) { sale ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = sale.productName,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "Qty: ${sale.quantity} · $${String.format("%.2f", sale.finalSellingPrice)}",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                    Text(
                                        text = "+$${String.format("%.1f", sale.profit)}",
                                        color = Color(0xFF10B981),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                            }
                        }
                    }
                }
            }

        Spacer(modifier = Modifier.height(24.dp))

        // Daily target configuration dialog
    if (showTargetDialog) {
        var targetText by remember { mutableStateOf(dailyTargetValue.toString()) }
        AlertDialog(
            onDismissRequest = { showTargetDialog = false },
            title = { Text("Configure Target") },
            text = {
                Column {
                    Text("Set your daily sales target to track progress.", fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = targetText,
                        onValueChange = { targetText = it },
                        label = { Text("Daily Target ($)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val targetDouble = targetText.toDoubleOrNull() ?: 500.0
                    viewModel.dailyTarget.value = targetDouble
                    showTargetDialog = false
                }) {
                    Text("SAVE")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTargetDialog = false }) {
                    Text("CANCEL")
                }
            }
        )
    }
}

data class KpiData(val title: String, val value: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val color: Color)

@Composable
fun CustomSalesChart(revenue: Double, profit: Double, inventory: Double) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        val maxVal = maxOf(revenue, profit, inventory, 100.0)
        val canvasWidth = size.width
        val canvasHeight = size.height

        val barWidth = 60.dp.toPx()
        val spacing = 40.dp.toPx()

        val data = listOf(
            ChartBarData("Revenue", revenue, Color(0xFF14B8A6)),
            ChartBarData("Net Profit", profit, Color(0xFF10B981)),
            ChartBarData("Stock Valuation", inventory, Color(0xFFF59E0B))
        )

        val totalBarsWidth = (barWidth * data.size) + (spacing * (data.size - 1))
        val startX = (canvasWidth - totalBarsWidth) / 2f

        data.forEachIndexed { i, bar ->
            val heightPercent = (bar.value / maxVal).toFloat()
            val barHeight = canvasHeight * 0.7f * heightPercent
            val x = startX + i * (barWidth + spacing)
            val y = canvasHeight * 0.8f - barHeight

            // Draw shadow or backdrop track
            drawRoundRect(
                color = Color.LightGray.copy(alpha = 0.2f),
                topLeft = Offset(x, canvasHeight * 0.1f),
                size = Size(barWidth, canvasHeight * 0.7f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )

            // Draw filled bar
            drawRoundRect(
                color = bar.color,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )

            // Draw value string
            drawContext.canvas.nativeCanvas.drawText(
                "$${String.format("%.0f", bar.value)}",
                x + barWidth / 2f,
                y - 15f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 30f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isFakeBoldText = true
                }
            )

            // Draw labels
            drawContext.canvas.nativeCanvas.drawText(
                bar.label,
                x + barWidth / 2f,
                canvasHeight * 0.92f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    textSize = 28f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }
    }
}

data class ChartBarData(val label: String, val value: Double, val color: Color)

// --- INVENTORY MODULE ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {
    val context = LocalContext.current
    val products by viewModel.filteredProducts.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val currentCategory by viewModel.categoryFilter.collectAsState()
    val currentBrand by viewModel.brandFilter.collectAsState()

    val availableCategories by viewModel.availableCategories.collectAsState()
    val availableBrands by viewModel.availableBrands.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var editingProduct by remember { mutableStateOf<Product?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
        // Search and Actions Bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.searchQuery.value = it },
                placeholder = { Text("Search perfume or brand...") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .testTag("search_field"),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .height(56.dp)
                    .testTag("add_product_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Perfume")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Advanced filter chips (Categories & Brands)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.FilterList, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
            Text("Category:", fontSize = 11.sp, fontWeight = FontWeight.Bold)

            availableCategories.forEach { cat ->
                InputChip(
                    selected = currentCategory == cat,
                    onClick = { viewModel.categoryFilter.value = cat },
                    label = { Text(cat, fontSize = 11.sp) },
                    colors = InputChipDefaults.inputChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.FilterList, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(16.dp))
            Text("Brand:", fontSize = 11.sp, fontWeight = FontWeight.Bold)

            availableBrands.forEach { brand ->
                InputChip(
                    selected = currentBrand == brand,
                    onClick = { viewModel.brandFilter.value = brand },
                    label = { Text(brand, fontSize = 11.sp) },
                    colors = InputChipDefaults.inputChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Stock Export Valuation Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${products.size} Perfumes Registered",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            TextButton(onClick = { viewModel.exportInventoryToCSV(context) }) {
                Icon(Icons.Filled.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Export Valuation CSV", fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Table List of Perfumes
        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "No perfumes match your search / filter criteria.",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(products) { item ->
                    PerfumeCard(
                        product = item,
                        onClick = { editingProduct = item }
                    )
                }
            }
        }
    }

    // Modal Sheet or dialog for adding a new perfume
    if (showAddDialog) {
        AddPerfumeDialog(
            onDismiss = { showAddDialog = false },
            onSave = { name, brand, cat, size, bPrice, sPrice, stock, notes, barcode ->
                viewModel.addProduct(name, brand, cat, size, bPrice, sPrice, stock, notes, null, barcode)
                showAddDialog = false
            }
        )
    }

    // Edit Product Sheet / Dialog
    editingProduct?.let { product ->
        EditPerfumeDialog(
            product = product,
            onDismiss = { editingProduct = null },
            onSave = { updatedProduct ->
                viewModel.updateProduct(updatedProduct)
                editingProduct = null
            },
            onDelete = {
                viewModel.deleteProduct(product)
                editingProduct = null
            }
        )
    }
}

@Composable
fun PerfumeCard(product: Product, onClick: () -> Unit) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        shape = androidx.compose.foundation.shape.CornerSize(24.dp),
        onClick = onClick
    ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = product.size,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${product.brand} · ${product.category}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                // Alert Chips
                if (product.isOutOfStock) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFFEAEA), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("OUT OF STOCK", color = WarningRed, fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                } else if (product.isLowStock) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFEF3C7), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("LOW STOCK", color = AmberAccent, fontSize = 10.sp, fontWeight = FontWeight.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Stock Status", fontSize = 10.sp, color = Color.Gray)
                    Text(
                        text = "${product.currentStock} items",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (product.isOutOfStock) WarningRed else MaterialTheme.colorScheme.onSurface
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Retail Price", fontSize = 10.sp, color = Color.Gray)
                    Text(
                        text = "$${String.format("%.2f", product.sellPrice)}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text("Stock Valuation", fontSize = 10.sp, color = Color.Gray)
                    Text(
                        text = "$${String.format("%.2f", product.stockValue)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
}

@Composable
fun AddPerfumeDialog(onDismiss: () -> Unit, onSave: (String, String, String, String, Double, Double, Int, String, String?) -> Unit) {
    var name by remember { mutableStateOf("") }
    var brand by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("3ml") } // Suggestion
    var buyPriceText by remember { mutableStateOf("") }
    var sellPriceText by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var barcode by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Perfume Product") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Product Name *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("add_name")
                )

                OutlinedTextField(
                    value = brand,
                    onValueChange = { brand = it },
                    label = { Text("Brand *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                // Size suggestion list or direct entry
                OutlinedTextField(
                    value = size,
                    onValueChange = { size = it },
                    label = { Text("Size (e.g. 3ml, 6ml, Spray) *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = buyPriceText,
                        onValueChange = { buyPriceText = it },
                        label = { Text("Buy Price ($) *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = sellPriceText,
                        onValueChange = { sellPriceText = it },
                        label = { Text("Sell Price ($) *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = stockText,
                        onValueChange = { stockText = it },
                        label = { Text("Initial Stock *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = barcode,
                        onValueChange = { barcode = it },
                        label = { Text("Barcode (Optional)") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (Oil grade, source...)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val buyP = buyPriceText.toDoubleOrNull() ?: 0.0
                    val sellP = sellPriceText.toDoubleOrNull() ?: 0.0
                    val initialStk = stockText.toIntOrNull() ?: 0
                    if (name.isNotBlank() && brand.isNotBlank() && category.isNotBlank() && size.isNotBlank()) {
                        onSave(name, brand, category, size, buyP, sellP, initialStk, notes, barcode.ifBlank { null })
                    }
                },
                modifier = Modifier.testTag("save_product_button")
            ) {
                Text("SAVE PRODUCT")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("CANCEL") }
        }
    )
}

@Composable
fun EditPerfumeDialog(product: Product, onDismiss: () -> Unit, onSave: (Product) -> Unit, onDelete: () -> Unit) {
    var name by remember { mutableStateOf(product.name) }
    var brand by remember { mutableStateOf(product.brand) }
    var category by remember { mutableStateOf(product.category) }
    var size by remember { mutableStateOf(product.size) }
    var buyPriceText by remember { mutableStateOf(product.buyPrice.toString()) }
    var sellPriceText by remember { mutableStateOf(product.sellPrice.toString()) }
    var stockText by remember { mutableStateOf(product.currentStock.toString()) }
    var notes by remember { mutableStateOf(product.notes) }
    var barcode by remember { mutableStateOf(product.barcode ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Perfume Profile") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Product Name") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Brand") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = size, onValueChange = { size = it }, label = { Text("Size") }, singleLine = true, modifier = Modifier.fillMaxWidth())

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = buyPriceText,
                        onValueChange = { buyPriceText = it },
                        label = { Text("Buy Price ($)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = sellPriceText,
                        onValueChange = { sellPriceText = it },
                        label = { Text("Sell Price ($)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = stockText,
                        onValueChange = { stockText = it },
                        label = { Text("Stock Level") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = barcode,
                        onValueChange = { barcode = it },
                        label = { Text("Barcode") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Notes") }, maxLines = 3, modifier = Modifier.fillMaxWidth())
            }
        },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = WarningRed)
                }
                Button(onClick = {
                    val buyP = buyPriceText.toDoubleOrNull() ?: product.buyPrice
                    val sellP = sellPriceText.toDoubleOrNull() ?: product.sellPrice
                    val currentStk = stockText.toIntOrNull() ?: product.currentStock
                    if (name.isNotBlank()) {
                        onSave(product.copy(name = name, brand = brand, category = category, size = size, buyPrice = buyP, sellPrice = sellP, currentStock = currentStk, notes = notes, barcode = barcode.ifBlank { null }))
                    }
                }) {
                    Text("SAVE CHANGES")
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("CANCEL") }
        }
    )
}

// --- SALES MODULE ---
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SalesScreen(viewModel: InventoryViewModel) {
    val products by viewModel.products.collectAsState()
    val recentSales by viewModel.sales.collectAsState()

    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantityText by remember { mutableStateOf("1") }
    var finalPriceText by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Date()) }
    var customerName by remember { mutableStateOf("") }
    var customerPhone by remember { mutableStateOf("") }
    var customerAddress by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    // Auto update sell price calculation
    val defaultPriceCalculated = selectedProduct?.let {
        val qty = quantityText.toIntOrNull() ?: 1
        it.sellPrice * qty
    } ?: 0.0

    // Set default price text if product or quantity changed and was not edited
    val finalPrice = finalPriceText.toDoubleOrNull() ?: defaultPriceCalculated
    val profit = selectedProduct?.let {
        val qty = quantityText.toIntOrNull() ?: 1
        finalPrice - (it.buyPrice * qty)
    } ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Record Perfume Sale", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Text("Record transactions below. System automatically updates stock levels, profit spreadsheets, and target widgets.", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Product selector Dropdown
                var expandedDropdown by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedDropdown = true }
                ) {
                    OutlinedTextField(
                        value = selectedProduct?.let { "${it.name} (${it.size}) · Stock: ${it.currentStock}" } ?: "Select Perfume *",
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("product_dropdown_sale"),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledBorderColor = MaterialTheme.colorScheme.outline,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    DropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        products.forEach { prod ->
                            DropdownMenuItem(
                                text = { Text("${prod.name} [${prod.brand}] (${prod.size}) — $${prod.sellPrice} (Stock: ${prod.currentStock})") },
                                onClick = {
                                    selectedProduct = prod
                                    finalPriceText = prod.sellPrice.toString() // Autofill default price
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }

                // Date selector field
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                OutlinedTextField(
                    value = dateFormatter.format(selectedDate),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sale Date") },
                    trailingIcon = {
                        IconButton(onClick = {
                            val calendar = Calendar.getInstance()
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val newCal = Calendar.getInstance().apply {
                                        set(Calendar.YEAR, year)
                                        set(Calendar.MONTH, month)
                                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                    }
                                    selectedDate = newCal.time
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }) {
                            Icon(Icons.Filled.FilterList, contentDescription = "Pick Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = quantityText,
                        onValueChange = { quantityText = it },
                        label = { Text("Quantity *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("sale_quantity"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = finalPriceText,
                        onValueChange = { finalPriceText = it },
                        placeholder = { Text(defaultPriceCalculated.toString()) },
                        label = { Text("Total Sale Price ($) *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("sale_price"),
                        singleLine = true
                    )
                }

                // Display dynamic Profit Calculation instantly!
                selectedProduct?.let {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(LightMint, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Automated Transaction Profit:", fontSize = 11.sp, color = DeepTeal)
                                Text(
                                    text = "$${String.format("%.2f", profit)}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = DeepTeal
                                )
                            }
                            Text(
                                text = "Cost Basis: $${String.format("%.1f", it.buyPrice * (quantityText.toIntOrNull() ?: 1))}",
                                fontSize = 11.sp,
                                color = DeepTeal.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                HorizontalDivider()

                // Customer Info (Optional)
                Text("Customer & CRM Details (Optional)", fontWeight = FontWeight.Bold, fontSize = 13.sp)

                OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    label = { Text("Customer Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = customerPhone,
                        onValueChange = { customerPhone = it },
                        label = { Text("Phone Number") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = customerAddress,
                        onValueChange = { customerAddress = it },
                        label = { Text("Address / City") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                // Alert errors
                errorMessage?.let {
                    Text(it, color = WarningRed, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                successMessage?.let {
                    Text(it, color = Color(0xFF10B981), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        val prod = selectedProduct
                        val qty = quantityText.toIntOrNull() ?: 0
                        if (prod == null) {
                            errorMessage = "Please select a perfume."
                            return@Button
                        }
                        if (qty <= 0) {
                            errorMessage = "Please enter a valid quantity."
                            return@Button
                        }
                        if (qty > prod.currentStock) {
                            errorMessage = "Prevented! Cannot sell more than available stock (${prod.currentStock} units)."
                            return@Button
                        }

                        viewModel.addSale(
                            productId = prod.id,
                            quantity = qty,
                            finalSellingPrice = finalPrice,
                            dateMillis = selectedDate.time,
                            customerName = customerName.ifBlank { null },
                            customerPhone = customerPhone.ifBlank { null },
                            customerAddress = customerAddress.ifBlank { null },
                            onSuccess = {
                                successMessage = "Sale registered successfully! Stock reduced."
                                errorMessage = null
                                // Clear Form
                                selectedProduct = null
                                quantityText = "1"
                                finalPriceText = ""
                                customerName = ""
                                customerPhone = ""
                                customerAddress = ""
                            },
                            onError = {
                                errorMessage = it
                                successMessage = null
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_sale_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("REGISTER TRANSACTION", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

// --- PURCHASES SCREEN ---
@Composable
fun PurchasesScreen(viewModel: InventoryViewModel) {
    val products by viewModel.products.collectAsState()

    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantityText by remember { mutableStateOf("") }
    var buyPriceText by remember { mutableStateOf("") }
    var supplier by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(Date()) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Record Inventory Purchase", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Text("Restock products below. System automatically increases stock and updates buy price basis.", fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Product Selector
                var expandedDropdown by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedDropdown = true }
                ) {
                    OutlinedTextField(
                        value = selectedProduct?.let { "${it.name} (${it.size}) · Stock: ${it.currentStock}" } ?: "Select Perfume to Restock *",
                        onValueChange = {},
                        readOnly = true,
                        enabled = false,
                        trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, contentDescription = null) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("purchase_product_dropdown"),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledBorderColor = MaterialTheme.colorScheme.outline,
                            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    DropdownMenu(
                        expanded = expandedDropdown,
                        onDismissRequest = { expandedDropdown = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        products.forEach { prod ->
                            DropdownMenuItem(
                                text = { Text("${prod.name} [${prod.brand}] (${prod.size}) — Current Cost: $${prod.buyPrice}") },
                                onClick = {
                                    selectedProduct = prod
                                    buyPriceText = prod.buyPrice.toString() // Autofill current buy cost
                                    expandedDropdown = false
                                }
                            )
                        }
                    }
                }

                // Date
                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                OutlinedTextField(
                    value = dateFormatter.format(selectedDate),
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Purchase Date") },
                    trailingIcon = {
                        IconButton(onClick = {
                            val calendar = Calendar.getInstance()
                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    val newCal = Calendar.getInstance().apply {
                                        set(Calendar.YEAR, year)
                                        set(Calendar.MONTH, month)
                                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                                    }
                                    selectedDate = newCal.time
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        }) {
                            Icon(Icons.Filled.FilterList, contentDescription = "Pick Date")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = quantityText,
                        onValueChange = { quantityText = it },
                        label = { Text("Quantity Purchased *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f).testTag("purchase_qty"),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = buyPriceText,
                        onValueChange = { buyPriceText = it },
                        label = { Text("Buy Price ($) *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = supplier,
                    onValueChange = { supplier = it },
                    label = { Text("Supplier / Distributer *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                errorMessage?.let {
                    Text(it, color = WarningRed, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                successMessage?.let {
                    Text(it, color = Color(0xFF10B981), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        val prod = selectedProduct
                        val qty = quantityText.toIntOrNull() ?: 0
                        val cost = buyPriceText.toDoubleOrNull() ?: 0.0

                        if (prod == null) {
                            errorMessage = "Please select a product."
                            return@Button
                        }
                        if (qty <= 0) {
                            errorMessage = "Quantity purchased must be positive."
                            return@Button
                        }
                        if (cost <= 0) {
                            errorMessage = "Cost price basis must be positive."
                            return@Button
                        }
                        if (supplier.isBlank()) {
                            errorMessage = "Please enter supplier details."
                            return@Button
                        }

                        viewModel.addPurchase(
                            productId = prod.id,
                            quantityPurchased = qty,
                            buyPrice = cost,
                            supplier = supplier,
                            dateMillis = selectedDate.time,
                            onContextClick = {
                                // No-op, using context helper
                            },
                            onSuccess = {
                                successMessage = "Restock saved successfully! Inventory stock increased."
                                errorMessage = null
                                selectedProduct = null
                                quantityText = ""
                                buyPriceText = ""
                                supplier = ""
                            },
                            onError = {
                                errorMessage = it
                                successMessage = null
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_purchase_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("LOG SUPPLIER PURCHASE", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

// Ext helper for signature mismatch prevention
fun InventoryViewModel.addPurchase(
    productId: Int,
    quantityPurchased: Int,
    buyPrice: Double,
    supplier: String,
    dateMillis: Long,
    onContextClick: () -> Unit,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    this.addPurchase(productId, quantityPurchased, buyPrice, supplier, dateMillis, onSuccess, onError)
}

// --- CUSTOMERS SCREEN ---
@Composable
fun CustomersScreen(viewModel: InventoryViewModel) {
    val customers by viewModel.customers.collectAsState()
    val sales by viewModel.sales.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var expandedCustomerProfile by remember { mutableStateOf<Customer?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("CRM & Customers", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                Text("Track purchases and client preferences easily.", fontSize = 12.sp, color = Color.Gray)
            }

            Button(onClick = { showAddDialog = true }, shape = RoundedCornerShape(8.dp)) {
                Icon(Icons.Filled.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Profile")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (customers.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No customer profiles stored. Records are automatically added on sale.", fontSize = 12.sp, color = Color.Gray)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(customers) { customer ->
                    // Purchase history for this customer
                    val history = sales.filter { s -> s.customerPhone == customer.phone }
                    val totalSalesValue = history.sumOf { it.finalSellingPrice }

                    GlassCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { expandedCustomerProfile = customer }
                    ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(customer.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                    Text("Phone: ${customer.phone}", fontSize = 12.sp, color = Color.Gray)
                                    if (customer.address.isNotBlank()) {
                                        Text("Address: ${customer.address}", fontSize = 12.sp, color = Color.Gray)
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text("Spent Total", fontSize = 10.sp, color = Color.Gray)
                                    Text("$${String.format("%.2f", totalSalesValue)}", fontWeight = FontWeight.Black, color = MaterialTheme.colorScheme.primary)
                                    Text("${history.size} purchases", fontSize = 11.sp, color = Color.Gray)
                                }
                            }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var name by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var address by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Create Customer Profile") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Customer Name *") }, singleLine = true)
                    OutlinedTextField(value = phone, onValueChange = { phone = it }, label = { Text("Phone Number *") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone), singleLine = true)
                    OutlinedTextField(value = address, onValueChange = { address = it }, label = { Text("Address") }, singleLine = true)
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (name.isNotBlank() && phone.isNotBlank()) {
                        viewModel.addCustomerProfile(name, phone, address)
                        showAddDialog = false
                    }
                }) {
                    Text("SAVE")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) { Text("CANCEL") }
            }
        )
    }

    // Expanded Purchase History Dialog
    expandedCustomerProfile?.let { customer ->
        val customerHistory = sales.filter { s -> s.customerPhone == customer.phone }
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        AlertDialog(
            onDismissRequest = { expandedCustomerProfile = null },
            title = { Text("${customer.name} - Purchase History") },
            text = {
                Column(modifier = Modifier.fillMaxWidth().heightIn(max = 400.dp)) {
                    Text("Phone: ${customer.phone}", fontSize = 12.sp, color = Color.Gray)
                    Text("Address: ${customer.address.ifBlank { "N/A" }}", fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    if (customerHistory.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No transaction history log.", fontSize = 12.sp, color = Color.Gray)
                        }
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(customerHistory) { sale ->
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(sale.productName, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("$${String.format("%.2f", sale.finalSellingPrice)}", fontWeight = FontWeight.Bold)
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Qty: ${sale.quantity} · Size: ${sale.size}", fontSize = 11.sp, color = Color.Gray)
                                        Text(df.format(Date(sale.dateMillis)), fontSize = 11.sp, color = Color.Gray)
                                    }
                                    HorizontalDivider(modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colorScheme.outlineVariant)
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Row {
                    TextButton(onClick = {
                        viewModel.deleteCustomerProfile(customer)
                        expandedCustomerProfile = null
                    }) {
                        Text("DELETE PROFILE", color = WarningRed)
                    }
                    Button(onClick = { expandedCustomerProfile = null }) {
                        Text("CLOSE")
                    }
                }
            }
        )
    }
}

// --- REPORTS SCREEN ---
@Composable
fun ReportsScreen(viewModel: InventoryViewModel) {
    val context = LocalContext.current
    var selectedReportType by remember { mutableStateOf(ReportType.DAILY) }

    val report = viewModel.generateReportData(selectedReportType)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Business Reports", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
                Text("Generate sales sheets, find dead stock, and export spreadsheets.", fontSize = 12.sp, color = Color.Gray)
            }

            IconButton(onClick = { viewModel.exportReportToCSV(context, selectedReportType) }) {
                Icon(Icons.Filled.Share, contentDescription = "Export Report", tint = MaterialTheme.colorScheme.primary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selector buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ReportType.values().forEach { type ->
                val isSelected = selectedReportType == type
                Button(
                    onClick = { selectedReportType = type },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(type.name, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Statistics Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GlassCard(
                modifier = Modifier.weight(1f)
            ) {
                Column {
                    Text("Revenue", fontSize = 11.sp, color = Color.Gray)
                    Text("$${String.format("%.2f", report.revenue)}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text("${report.salesCount} transactions", fontSize = 10.sp, color = Color.Gray)
                }
            }

            GlassCard(
                modifier = Modifier.weight(1f)
            ) {
                Column {
                    Text("Total Net Profit", fontSize = 11.sp, color = Color.Gray)
                    Text(
                        text = "$${String.format("%.2f", report.profit)}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF10B981)
                    )
                    Text("${report.quantitySold} units sold", fontSize = 10.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Best Sellers Segment
        Text("Best Selling Perfumes", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (report.topSelling.isEmpty()) {
                    Text("No transactions logged in this cycle.", fontSize = 12.sp, color = Color.Gray)
                } else {
                    report.topSelling.take(5).forEach { top ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(top.name, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("${top.totalQuantity} units · $${String.format("%.2f", top.totalRevenue)}", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Dead Stock Segment
        Text("Dead Stock (Products with 0 Sales)", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = WarningRed)
        Spacer(modifier = Modifier.height(8.dp))
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (report.deadStock.isEmpty()) {
                    Text("Optimal! All products have registered at least one sale.", fontSize = 12.sp, color = Color.Gray)
                } else {
                    report.deadStock.forEach { product ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(product.name, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Text("Stock: ${product.currentStock} · Size: ${product.size}", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
