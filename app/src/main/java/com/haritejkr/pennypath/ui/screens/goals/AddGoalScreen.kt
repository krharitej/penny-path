package com.haritejkr.pennypath.ui.screens.goals

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.haritejkr.pennypath.model.Goal
import com.haritejkr.pennypath.ui.navigation.Routes

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun AddGoalScreen(
    navController: NavHostController
) {

    val parentEntry = remember(navController) {
        navController.getBackStackEntry(Routes.Goals.route)
    }
    val viewModel: GoalViewModel = hiltViewModel(parentEntry)

    var name by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Goal Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = target,
            onValueChange = { target = it },
            label = { Text("Target Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                viewModel.addGoal(
                    Goal(
                        id = System.currentTimeMillis(),
                        name = name,
                        targetAmount = target.toDoubleOrNull() ?: 0.0,
                        savedAmount = 0.0,
                        deadline = System.currentTimeMillis()
                    )
                )
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Goal")
        }
    }
}