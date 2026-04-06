package com.haritejkr.pennypath.ui.screens.transaction

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

@OptIn(ExperimentalMaterial3Api::class)
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        items(transactions) { transaction ->

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
                }
            )
        }
    }
}