package com.haritejkr.pennypath.ui.drawer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.haritejkr.pennypath.model.Transaction
import com.haritejkr.pennypath.ui.components.FabMenu
import com.haritejkr.pennypath.ui.components.FilterBottomSheet
import com.haritejkr.pennypath.ui.navigation.AppNavGraph
import com.haritejkr.pennypath.ui.navigation.Routes
import com.haritejkr.pennypath.ui.screens.transaction.TransactionViewModel
import kotlinx.coroutines.launch
import kotlin.text.clear

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(viewModel: TransactionViewModel = hiltViewModel()) {

    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    var isSelectionMode by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateListOf<Transaction>() }

    var isEditMode by remember { mutableStateOf(false) }

    var showFilterSheet by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentRoute = currentRoute ?: Routes.Dashboard.route,
                onItemClick = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(Routes.Dashboard.route)
                        launchSingleTop = true
                    }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(
                        when (currentRoute) {
                            "transactions" ->
                                if (isSelectionMode) "Select Transactions" else "Transactions"
                            "dashboard" -> "Dashboard"
                            "insights" -> "Insights"
                            "goals" -> "Goals"

                            else -> "PennyPath"
                        }
                    ) },
                    actions = {
                        if(isSelectionMode){
                            IconButton(onClick = {
                                selectedItems.forEach {
                                    viewModel.deleteTransaction(it)
                                }
                                selectedItems.clear()
                                isSelectionMode = false
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open()
                                else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { padding ->

            Box(modifier = Modifier.padding(padding)
                .fillMaxSize()) {
                AppNavGraph(
                    viewModel = viewModel,
                    navController = navController,
                    isSelectionMode = isSelectionMode,
                    isEditMode = isEditMode,
                    selectedItems = selectedItems,
                    onSelectToggle = { transaction, selected ->
                        if (selected) selectedItems.add(transaction)
                        else selectedItems.remove(transaction)
                    },
                    onEditClick = { transaction ->
                        isEditMode = false
                        val id = transaction.id
                        println("Edit clicked ID: $id")   // debug
                        navController.navigate("update_transaction/${transaction.id}")
                    }
                )
                if (showFilterSheet) {
                    FilterBottomSheet(
                        onDismiss = { showFilterSheet = false },
                        onApply = { type, category, min, max ->
                            showFilterSheet = false
                            println("APPLY CLICKED: $type $category $min $max")
                            viewModel.applyFilters(
                                type,
                                category,
                                min,
                                max
                            )
                        }
                    )
                }

                if (currentRoute == "transactions") {
                    Box(modifier = Modifier.fillMaxSize()) {

                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .animateContentSize()
                            ) {
                                FabMenu(
                                    onAdd = { navController.navigate("add_transaction") },
                                    onUpdate = { isEditMode = true },
                                    onDelete = { isSelectionMode = true }
                                )
                            }

                            FloatingActionButton(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(end = 80.dp, bottom = 15.dp),
                                onClick = { showFilterSheet = true }
                            ) {
                                Icon(Icons.Default.FilterList, contentDescription = null)
                            }
                        }
                    }
                }
            }
        }
    }
}