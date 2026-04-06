package com.haritejkr.pennypath.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FabMenu(
    onAdd: () -> Unit,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {

        // Delete
        AnimatedVisibility(
            visible = expanded,
            enter = slideInHorizontally(),
            exit = slideOutVertically()
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    expanded = false
                    onDelete()
                },
                icon = { Icon(Icons.Default.Delete, contentDescription = null) },
                text = { Text("Delete") }
            )
        }

        // Update
        AnimatedVisibility(
            visible = expanded,
            enter = slideInHorizontally(),
            exit = slideOutVertically()
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    expanded = false
                    onUpdate()
                },
                icon = { Icon(Icons.Default.Edit, contentDescription = null) },
                text = { Text("Update") }
            )
        }

        // Add
        AnimatedVisibility(
            visible = expanded,
            enter = slideInHorizontally(),
            exit = slideOutVertically()
        ) {
            ExtendedFloatingActionButton(
                onClick = {
                    expanded = false
                    onAdd()
                },
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Add") }
            )
        }

        // Main FAB
        FloatingActionButton(
            onClick = { expanded = !expanded }
        ) {
            if(!expanded) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Menu"
                )
            }
            else {
                Icon(
                    imageVector = Icons.Outlined.Cancel,
                    contentDescription = "Menu"
                )
            }
        }
    }
}