package com.haritejkr.pennypath.ui.drawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haritejkr.pennypath.ui.navigation.Routes

@Composable
fun DrawerContent(
    currentRoute: String,
    onItemClick: (String) -> Unit
) {

    ModalDrawerSheet {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "PennyPath",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        NavigationDrawerItem(
            label = { Text("Dashboard") },
            selected = currentRoute == Routes.Dashboard.route,
            onClick = { onItemClick(Routes.Dashboard.route) },
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Transactions") },
            selected = currentRoute == Routes.Transactions.route,
            onClick = { onItemClick(Routes.Transactions.route) },
            icon = { Icon(Icons.Default.List, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Insights") },
            selected = currentRoute == Routes.Insights.route,
            onClick = { onItemClick(Routes.Insights.route) },
            icon = { Icon(Icons.Default.Analytics, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        NavigationDrawerItem(
            label = { Text("Goals") },
            selected = currentRoute == Routes.Goals.route,
            onClick = { onItemClick(Routes.Goals.route) },
            icon = { Icon(Icons.Default.Flag, contentDescription = null) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}