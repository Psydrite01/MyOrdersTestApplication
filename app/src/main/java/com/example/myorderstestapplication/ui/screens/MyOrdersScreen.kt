package com.example.myorderstestapplication.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ─── Palette ────────────────────────────────────────────────────────────────
private val YellowPrimary  = Color(0xFFFFC107)
private val YellowDark     = Color(0xFFF9A825)
private val BgGray         = Color(0xFFF5F5F5)
private val CardWhite      = Color.White
private val TextDark       = Color(0xFF1A1A1A)
private val TextMedium     = Color(0xFF555555)
private val TextLight      = Color(0xFF666666)
private val CancelledRed   = Color(0xFFE53935)
private val InfoBlue       = Color(0xFF1565C0)
private val InfoBlueBg     = Color(0xFFE8EAF6)
private val PickupGreen    = Color(0xFF43A047)
private val DropRed        = Color(0xFFE53935)
private val DividerGray    = Color(0xFFE0E0E0)

// ─── Data model ─────────────────────────────────────────────────────────────
enum class OrderStatus { CANCELLED, COMPLETED, BOOKED_AGAIN }

data class Order(
    val id: String,
    val vehicleType: String,
    val dateTime: String,
    val price: Double,
    val pickupAddress: String,
    val dropAddress: String,
    val status: OrderStatus
)

// ─── Sample data ─────────────────────────────────────────────────────────────
private val sampleOrders = listOf(
    Order(
        id = "#ORD12345",
        vehicleType = "Four Wheeler",
        dateTime = "05 Feb, 4:46 PM",
        price = 229.0,
        pickupAddress = "741, Gumanwara",
        dropAddress = "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
        status = OrderStatus.CANCELLED
    ),
    Order(
        id = "#ORD12346",
        vehicleType = "Four Wheeler",
        dateTime = "05 Feb, 4:46 PM",
        price = 229.0,
        pickupAddress = "741, Gumanwara",
        dropAddress = "00, Main Rd, Shivaji Nagar, Jhansi, Uttar Pradesh 284001, India",
        status = OrderStatus.CANCELLED
    ),
    Order(
        id = "#ORD12347",
        vehicleType = "Four Wheeler",
        dateTime = "05 Feb, 4:46 PM",
        price = 1515.0,
        pickupAddress = "332, Gumanwara",
        dropAddress = "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
        status = OrderStatus.CANCELLED
    ),
    Order(
        id = "#ORD12348",
        vehicleType = "Four Wheeler",
        dateTime = "05 Feb, 4:46 PM",
        price = 1634.0,
        pickupAddress = "332, Gumanwara",
        dropAddress = "GC72+GGV, Kamrari, Madhya Pradesh 475661, India",
        status = OrderStatus.CANCELLED
    ),
    Order(
        id = "#ORD12349",
        vehicleType = "Four Wheeler",
        dateTime = "06 Feb, 10:00 AM",
        price = 850.0,
        pickupAddress = "12, MG Road",
        dropAddress = "45, Gandhi Nagar, Bhopal, Madhya Pradesh 462001, India",
        status = OrderStatus.COMPLETED
    )
)

// ─── Filter tabs ──────────────────────────────────────────────────────────────
private val filterTabs = listOf("All Orders", "Completed", "Cancelled", "Booked Again")

// ─── Screen ───────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var infoBannerVisible by remember { mutableStateOf(true) }

    val filteredOrders = remember(selectedTab, searchQuery) {
        val byTab = when (selectedTab) {
            1 -> sampleOrders.filter { it.status == OrderStatus.COMPLETED }
            2 -> sampleOrders.filter { it.status == OrderStatus.CANCELLED }
            3 -> sampleOrders.filter { it.status == OrderStatus.BOOKED_AGAIN }
            else -> sampleOrders
        }
        if (searchQuery.isBlank()) byTab
        else byTab.filter {
            it.id.contains(searchQuery, ignoreCase = true) ||
                    it.pickupAddress.contains(searchQuery, ignoreCase = true) ||
                    it.dropAddress.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgGray)
    ) {
        // ── Header ──────────────────────────────────────────────────────────
        MyOrdersHeader()

        // ── Scrollable content ───────────────────────────────────────────────
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BgGray),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Search + filter row
            item {
                SearchFilterRow(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
            }

            // Filter tabs
            item {
                FilterTabRow(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
            }

            // Info banner
            if (infoBannerVisible) {
                item {
                    InfoBanner(onDismiss = { infoBannerVisible = false })
                }
            }

            // Order cards
            items(filteredOrders) { order ->
                OrderCard(order = order)
            }

            if (filteredOrders.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 64.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No orders found",
                            color = TextLight,
                            fontSize = 16.sp
                        )
                    }
                }
            }

            item {
                Spacer(Modifier.height(120.dp))
            }
        }
    }
}

// ─── Header ──────────────────────────────────────────────────────────────────
@Composable
fun MyOrdersHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(YellowPrimary)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.65f)) {
            Text(
                text = "My Orders",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "View your completed trips here. You can download invoices or quickly book the same order again.",
                fontSize = 12.sp,
                color = TextDark.copy(alpha = 0.75f),
                lineHeight = 17.sp
            )
        }

        // Placeholder truck illustration
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(width = 110.dp, height = 80.dp)
                .background(Color.White)
        ) {
            Text("Image placeholder",
                modifier = Modifier.align(Alignment.Center))
        }
    }
}

// ─── Search + filter/sort row ─────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterRow(query: String, onQueryChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Search field
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = "Search by Order ID / Location",
                    fontSize = 13.sp,
                    color = TextLight
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextLight,
                    modifier = Modifier.size(20.dp)
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = YellowPrimary,
                unfocusedBorderColor = DividerGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier
                .weight(1f)
                .height(52.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 11.sp)
        )

        // Filter button
        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(
                width = 1.dp
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            modifier = Modifier.height(52.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown, // placeholder for filter icon
                contentDescription = "Filter",
                tint = TextDark,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("Filter", color = TextDark, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }

        // Sort button
        OutlinedButton(
            onClick = {},
            shape = RoundedCornerShape(10.dp),
            border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            modifier = Modifier.height(52.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Sort",
                tint = TextDark,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text("Sort", color = TextDark, fontSize = 13.sp, fontWeight = FontWeight.Medium)
        }
    }
}

// ─── Filter tab row ───────────────────────────────────────────────────────────
@Composable
fun FilterTabRow(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        filterTabs.forEachIndexed { index, label ->
            val isSelected = selectedTab == index
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) YellowPrimary else Color.Transparent)
                    .clickable { onTabSelected(index) }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = TextDark
                )
            }
        }
    }
}

// ─── Info banner ──────────────────────────────────────────────────────────────
@Composable
fun InfoBanner(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .background(InfoBlueBg, RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = InfoBlue,
            modifier = Modifier
                .size(20.dp)
                .padding(top = 2.dp)
        )
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "यहाँ आपके सभी पिछले ऑर्डर दिखाए गए हैं।",
                fontSize = 12.sp,
                color = TextDark,
                fontWeight = FontWeight.Medium,
                lineHeight = 18.sp
            )
            Text(
                text = "आप इनवॉइस डाउनलोड कर सकते हैं या उसी पते पर दोबारा ऑर्डर कर सकते हैं।",
                fontSize = 11.sp,
                color = TextDark.copy(alpha = 0.9f),
                lineHeight = 17.sp
            )
        }
        Spacer(Modifier.width(6.dp))
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Dismiss",
            tint = TextDark,
            modifier = Modifier
                .size(18.dp)
                .clickable { onDismiss() }
        )
    }
}

// ─── Order card ───────────────────────────────────────────────────────────────
@Composable
fun OrderCard(order: Order) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            // ── Top row: truck icon + vehicle info + price + kebab ─────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Truck placeholder
                Box(
                    modifier = Modifier
                        .size(width = 72.dp, height = 50.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(YellowPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Icon placeholder",
                        Modifier.align(Alignment.Center),
                        fontSize = 11.sp)
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = order.vehicleType,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "${order.dateTime}  |  Order ID: ${order.id}",
                        fontSize = 11.5.sp,
                        color = TextLight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(Modifier.width(8.dp))

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "₹ ${order.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDark
                    )
                    Spacer(Modifier.height(2.dp))
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = TextLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            // ── Address rows ──────────────────────────────────────────────
            AddressRow(
                isPickup = true,
                address = order.pickupAddress
            )
            Spacer(Modifier.height(6.dp))
            AddressRow(
                isPickup = false,
                address = order.dropAddress
            )

            Spacer(Modifier.height(14.dp))

            // ── Bottom row: status + actions ──────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Status chip
                StatusChip(status = order.status)

                Spacer(Modifier.weight(1f))

                // Invoice button
                OutlinedButton(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FileDownload,
                        contentDescription = "Download Invoice",
                        tint = TextDark,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Invoice",
                        fontSize = 13.sp,
                        color = TextDark,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(Modifier.width(8.dp))

                // Book Again button
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowPrimary,
                        contentColor = TextDark
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = "Book Again",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDark
                    )
                }
            }
        }
    }
}

// ─── Address row ──────────────────────────────────────────────────────────────
@Composable
fun AddressRow(isPickup: Boolean, address: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        // Location icon
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Location",
            modifier = Modifier
                .size(16.dp),
            tint = if (isPickup) PickupGreen else DropRed
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = address,
            fontSize = 13.sp,
            color = TextMedium,
            lineHeight = 18.sp
        )
    }
}

// ─── Status chip ─────────────────────────────────────────────────────────────
@Composable
fun StatusChip(status: OrderStatus) {
    val (label, bg, fg) = when (status) {
        OrderStatus.CANCELLED -> Triple(
            "CANCELLED",
            CancelledRed.copy(alpha = 0.1f),
            CancelledRed
        )
        OrderStatus.COMPLETED -> Triple(
            "COMPLETED",
            PickupGreen.copy(alpha = 0.1f),
            PickupGreen
        )
        OrderStatus.BOOKED_AGAIN -> Triple(
            "BOOKED AGAIN",
            YellowPrimary.copy(alpha = 0.2f),
            YellowDark
        )
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = fg,
            letterSpacing = 0.5.sp
        )
    }
}