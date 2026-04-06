package com.haritejkr.pennypath.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.haritejkr.pennypath.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionItem(
    transaction: Transaction,
    isSelectionMode: Boolean,
    isEditMode: Boolean,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val type = transaction.type.lowercase()

    val amountColor = when (type) {
        "income" -> Color(0xFF00E676)   // neon green
        "expense" -> Color(0xFFFF5252)  // neon red
        "savings" -> Color(0xFFFFC107)  // amber glow
        else -> Color.Gray
    }

    val icon = when (type) {
        "savings" -> Icons.Default.AccountBalanceWallet
        else -> getCategoryIcon(transaction.category, type == "income")
    }

    // 🔥 ENTRY ANIMATION
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(400),
        label = ""
    )

    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 20.dp,
        animationSpec = tween(400),
        label = ""
    )

    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.96f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        ),
        label = ""
    )

    // 💎 GLASS CARD
    GlassCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .graphicsLayer {
                this.alpha = alpha
                translationY = offsetY.toPx()
                scaleX = scale
                scaleY = scale
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ✅ Checkbox
            if (isSelectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onSelect(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            // 💎 Icon with glow feel
            Surface(
                shape = CircleShape,
                color = amountColor.copy(alpha = 0.15f),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = amountColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 📄 Middle Content
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = if (transaction.type == "savings") "Savings"
                    else transaction.category,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = formatDate(transaction.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // 💰 Right Content
            Column(horizontalAlignment = Alignment.End) {

                Text(
                    text = "₹${transaction.amount.toInt()}",
                    color = amountColor,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            when (type) {
                                "income" -> "Income"
                                "expense" -> "Expense"
                                "savings" -> "Savings"
                                else -> ""
                            }
                        )
                    },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = amountColor.copy(alpha = 0.15f),
                        labelColor = amountColor
                    )
                )
            }

            // ✏️ Edit button
            if (isEditMode) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
        }
    }
}

fun getCategoryIcon(category: String, isIncome: Boolean) = when (category.lowercase()) {
    "food" -> Icons.Default.Fastfood
    "travel" -> Icons.Default.DirectionsCar
    "shopping" -> Icons.Default.ShoppingCart
    "salary" -> Icons.Default.Work
    else -> if (isIncome) Icons.Default.CallReceived else Icons.Default.CallMade
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(timestamp))
}