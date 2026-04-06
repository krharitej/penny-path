package com.haritejkr.pennypath.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    onEditClick: () -> Unit
) {

    val type = transaction.type.lowercase()

    val amountColor = when (type) {
        "income" -> Color(0xFF2E7D32)
        "expense" -> Color(0xFFC62828)
        "savings" -> Color(0xFFFF9800)
        else -> Color.Gray
    }

    val icon = when (type) {
        "savings" -> Icons.Default.AccountBalanceWallet
        else -> getCategoryIcon(transaction.category, type == "income")
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Checkbox (only in delete mode)
            if (isSelectionMode) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { onSelect(it) }
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            // Icon
            Surface(
                shape = CircleShape,
                color = amountColor.copy(alpha = 0.12f),
                modifier = Modifier.size(40.dp)
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

            // Middle Content
            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = if (transaction.type == "savings") "Savings"
                    else transaction.category,
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = formatDate(transaction.date),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Right Content
            Column(horizontalAlignment = Alignment.End) {

                Text(
                    text = "₹${transaction.amount}",
                    color = amountColor,
                    style = MaterialTheme.typography.bodyLarge
                )

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
                        containerColor = amountColor.copy(alpha = 0.1f),
                        labelColor = amountColor
                    )
                )
            }
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