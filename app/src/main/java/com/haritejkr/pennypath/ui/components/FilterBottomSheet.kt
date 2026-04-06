package com.haritejkr.pennypath.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FilterBottomSheet(
    onDismiss: () -> Unit,
    onApply: (String?, String?, Float, Float) -> Unit
) {

    var selectedType by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var minAmount by remember { mutableStateOf(0f) }
    var maxAmount by remember { mutableStateOf(500000f) }

    var minText by remember { mutableStateOf("0") }
    var maxText by remember { mutableStateOf("500000") }

    val categories = listOf("Food", "Travel", "Shopping", "Salary", "Other")

    var customCategory by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Filters", style = MaterialTheme.typography.titleLarge)

            // Type chips
            Text("Type")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = selectedType == "income",
                    onClick = { selectedType = "income" },
                    label = { Text("Income") }
                )
                FilterChip(
                    selected = selectedType == "expense",
                    onClick = { selectedType = "expense" },
                    label = { Text("Expense") }
                )
                FilterChip(
                    selected = selectedType == "savings",
                    onClick = { selectedType = "savings" },
                    label = { Text("Savings") }
                )
            }

            // Category chips
            Text("Category")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categories.forEach {
                    FilterChip(
                        selected = selectedCategory == it,
                        onClick = { selectedCategory = it },
                        label = { Text(it) }
                    )
                }
            }
            if (selectedCategory == "Other") {
                OutlinedTextField(
                    value = customCategory,
                    onValueChange = { customCategory = it },
                    label = { Text("Enter category") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Amount slider
            Text("Amount Range")
            RangeSlider(
                value = minAmount..maxAmount,
                onValueChange = {
                    minAmount = it.start
                    maxAmount = it.endInclusive
                    minText = minAmount.toInt().toString()
                    maxText = maxAmount.toInt().toString()
                },
                valueRange = 0f..500000f
            )

            Text("₹${minAmount.toInt()} - ₹${maxAmount.toInt()}")

            // Manual amount fields
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = minText,
                    onValueChange = {
                        minText = it
                        val value = it.toFloatOrNull()
                        if (value != null && value <= maxAmount) {
                            minAmount = value
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Min") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                OutlinedTextField(
                    value = maxText,
                    onValueChange = {
                        maxText = it
                        val value = it.toFloatOrNull()
                        if (value != null && value >= minAmount) {
                            maxAmount = value
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Max") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }

            // Buttons
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                TextButton(
                    onClick = {
                        onApply(null, null, 0f, Float.MAX_VALUE)
                    }
                ) {
                    Text("Clear Filters")
                }

                Button(
                    onClick = {
                        var finalCategory = if (selectedCategory == "Other") {
                            customCategory.takeIf { it.isNotBlank() }
                        } else {
                            selectedCategory
                        }
                        finalCategory = finalCategory?.trim()
                        onApply(
                            selectedType,
                            finalCategory,
                            minAmount,
                            maxAmount
                        )
                    }
                ) {
                    Text("Apply")
                }
            }
        }
    }
}