package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishDetailScreen(
    dishId: Int,
    viewModel: DishViewModel,
    onBack: () -> Unit
) {
    val allDishes by viewModel.dishes.collectAsStateWithLifecycle()
    val currentDish = allDishes.find { it.id == dishId }

    if (currentDish == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Dish not found")
        }
        return
    }

    var stepInput by remember { mutableStateOf("") }
    var editingStep by remember { mutableStateOf<Recipe?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentDish.name, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = stepInput,
                    onValueChange = { stepInput = it },
                    placeholder = { Text("Next step...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                Spacer(Modifier.width(8.dp))
                FilledIconButton(
                    onClick = {
                        viewModel.addRecipe(dishId, stepInput)
                        stepInput = ""
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Add", Modifier.size(20.dp))
                }
            }

            Spacer(Modifier.height(24.dp))
            
            Text("Instructions", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                itemsIndexed(items = currentDish.recipes, key = { _, r -> r.id }) { index, recipe ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        ListItem(
                            leadingContent = { 
                                Surface(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text((index + 1).toString(), color = Color.White, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
                                    }
                                }
                            },
                            headlineContent = { Text(recipe.text, style = MaterialTheme.typography.bodyMedium) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            trailingContent = {
                                Row {
                                    IconButton(onClick = { editingStep = recipe }) {
                                        Icon(Icons.Default.Edit, null, Modifier.size(18.dp))
                                    }
                                    IconButton(onClick = { viewModel.deleteRecipe(dishId, recipe.id) }) {
                                        Icon(Icons.Default.Delete, null, Modifier.size(18.dp), tint = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    editingStep?.let { recipe ->
        EditDialog(
            title = "Edit Step",
            initialText = recipe.text,
            onConfirm = { updatedText ->
                viewModel.updateRecipe(dishId, recipe.id, updatedText)
                editingStep = null
            }
        ) { editingStep = null }
    }
}
