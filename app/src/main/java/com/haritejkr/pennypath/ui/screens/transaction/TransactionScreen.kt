package com.haritejkr.pennypath.ui.screens.transaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.Alignment
import com.haritejkr.pennypath.ui.components.FabMenu
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haritejkr.pennypath.model.Transaction
import com.haritejkr.pennypath.ui.components.TransactionItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel,
    isSelectionMode: Boolean,
    isEditMode: Boolean,
    selectedItems: List<Transaction>,
    onSelectToggle: (Transaction, Boolean) -> Unit,
    onEditClick: (Transaction) -> Unit
) {

    val transactions by viewModel.transactions.collectAsState(initial = emptyList())

    val grouped = transactions.groupBy { transaction ->
        val today = Calendar.getInstance()
        val txnDate = Calendar.getInstance().apply {
            timeInMillis = transaction.date
        }

        when {
            isSameDay(today, txnDate) -> "Today"
            isSameDay(
                Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) },
                txnDate
            ) -> "Yesterday"
            else -> SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                .format(Date(transaction.date))
        }
    }
    if (transactions.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No transactions yet 🚀")
        }
        return
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {

        grouped.forEach { (date, list) ->

            item {
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(list, key = { it.id }) { transaction ->

                val isSelected = selectedItems.contains(transaction)

                TransactionItem(
                    transaction = transaction,
                    isSelectionMode = isSelectionMode,
                    isEditMode = isEditMode,
                    isSelected = isSelected,
                    onSelect = { selected ->
                        onSelectToggle(transaction, selected)
                    },
                    onEditClick = {
                        onEditClick(transaction)
                    },
                    Modifier
                        .animateItemPlacement()
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }
        }
    }
}
fun isSameDay(cal1: Calendar, cal2: Calendar): Boolean {
    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
}