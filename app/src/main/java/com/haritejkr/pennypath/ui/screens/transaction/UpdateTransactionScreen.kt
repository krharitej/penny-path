package com.haritejkr.pennypath.ui.screens.transaction

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.haritejkr.pennypath.ui.components.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTransactionScreen(
    transactionId: Long? = null,
    viewModel: TransactionViewModel = hiltViewModel(),
    navController: NavHostController
) {

    var amount by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("expense") }
    var category by remember { mutableStateOf("Food") }
    var customCategory by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    val categories = listOf("Food", "Travel", "Shopping", "Salary", "Other")

    var selectedDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var showDatePicker by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // Amount
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        //date

        OutlinedButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Date: ${formatDate(selectedDate)}")
        }
        if (showDatePicker) {

            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = selectedDate
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        selectedDate =
                            datePickerState.selectedDateMillis ?: selectedDate
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        // Type selector (Segmented)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("income", "expense", "savings").forEach { t ->
                FilterChip(
                    selected = type == t,
                    onClick = { type = t },
                    label = { Text(t.replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        // Category Dropdown
        var expanded by remember { mutableStateOf(false) }

        if(type != "savings"){
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                category = it
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Custom category
            if (category == "Other") {
                OutlinedTextField(
                    value = customCategory,
                    onValueChange = { customCategory = it },
                    label = { Text("Enter Category") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Note
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note") },
            modifier = Modifier.fillMaxWidth()
        )

        // Save button
        Button(
            onClick = {
                val finalCategory = when {
                    type == "savings" -> "Savings"
                    category == "Other" -> customCategory
                    else -> category
                }

                if (transactionId == null) {
                    viewModel.addTransaction(
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        type = type,
                        category = finalCategory,
                        note = note,
                        date = selectedDate
                    )
                } else {
                    viewModel.updateTransaction(
                        id = transactionId.toLong(),
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        type = type,
                        category = finalCategory,
                        note = note,
                        date = selectedDate
                    )
                }

                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (transactionId == null) "Add Transaction" else "Update Transaction")
        }
    }

    LaunchedEffect(transactionId) {
        transactionId?.let { id ->
            viewModel.getTransaction(id) { transaction ->
                amount = transaction.amount.toString()
                type = transaction.type
                category = transaction.category
                note = transaction.note
                selectedDate = transaction.date
            }
        }
    }
}