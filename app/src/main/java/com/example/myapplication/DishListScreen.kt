package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishListScreen(
    viewModel: DishViewModel,
    onDishClick: (Int) -> Unit
) {
    val dishes by viewModel.dishes.collectAsStateWithLifecycle()
    var inputName by remember { mutableStateOf("") }
    var editingDish by remember { mutableStateOf<Dish?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyRecipeBook", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            OutlinedTextField(
                value = inputName,
                onValueChange = { inputName = it },
                placeholder = { Text("What are we cooking?") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.addDish(inputName)
                        inputName = ""
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                singleLine = true
            )

            Spacer(Modifier.height(24.dp))
            
            Text(
                "Your Collection", 
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            
            Spacer(Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(items = dishes, key = { it.id }) { dish ->
                    Surface(
                        onClick = { onDishClick(dish.id) },
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        ListItem(
                            headlineContent = { Text(dish.name, fontWeight = FontWeight.SemiBold) },
                            supportingContent = { Text("${dish.recipes.size} steps") },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            trailingContent = {
                                Row {
                                    IconButton(onClick = { editingDish = dish }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit", Modifier.size(20.dp))
                                    }
                                    IconButton(onClick = { viewModel.deleteDish(dish.id) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete", Modifier.size(20.dp), tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    editingDish?.let { dish ->
        EditDialog(
            title = "Rename Dish",
            initialText = dish.name,
            onConfirm = { newName ->
                viewModel.updateDish(dish.id, newName)
                editingDish = null
            }
        ) { editingDish = null }
    }
}

@Composable
fun EditDialog(
    title: String,
    initialText: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var textState by remember { mutableStateOf(initialText) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            OutlinedTextField(
                value = textState,
                onValueChange = { textState = it },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
        },
        confirmButton = {
            Button(onClick = { onConfirm(textState) }) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
