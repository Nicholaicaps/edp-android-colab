package com.example.myapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DishViewModel : ViewModel() {
    private val _dishes = MutableStateFlow(
        listOf(
            Dish(id = 1, name = "Lumpiang Shanghai"),
            Dish(id = 2, name = "Lechon Kawali"),
            Dish(id = 3, name = "Bulalo")
        )
    )
    val dishes: StateFlow<List<Dish>> = _dishes.asStateFlow()

    private var currentIdCounter = 100

    fun addDish(name: String) {
        val cleanName = name.trim()
        if (cleanName.isEmpty()) return
        
        val newEntry = Dish(id = currentIdCounter++, name = cleanName)
        _dishes.update { it + newEntry }
    }

    fun getDish(dishId: Int): Dish? {
        return _dishes.value.find { it.id == dishId }
    }

    fun updateDish(dishId: Int, newName: String) {
        val cleanName = newName.trim()
        if (cleanName.isEmpty()) return
        
        _dishes.update { list ->
            list.map { if (it.id == dishId) it.copy(name = cleanName) else it }
        }
    }

    fun deleteDish(dishId: Int) {
        _dishes.update { list ->
            list.filter { it.id != dishId }
        }
    }

    fun addRecipe(dishId: Int, text: String) {
        val cleanText = text.trim()
        if (cleanText.isEmpty()) return
        
        _dishes.update { list ->
            list.map { dish ->
                if (dish.id == dishId) {
                    val newRecipe = Recipe(id = currentIdCounter++, text = cleanText)
                    dish.copy(recipes = dish.recipes + newRecipe)
                } else dish
            }
        }
    }

    fun updateRecipe(dishId: Int, recipeId: Int, newText: String) {
        val cleanText = newText.trim()
        if (cleanText.isEmpty()) return

        _dishes.update { list ->
            list.map { dish ->
                if (dish.id == dishId) {
                    dish.copy(recipes = dish.recipes.map { recipe ->
                        if (recipe.id == recipeId) recipe.copy(text = cleanText) else recipe
                    })
                } else dish
            }
        }
    }

    fun deleteRecipe(dishId: Int, recipeId: Int) {
        _dishes.update { list ->
            list.map { dish ->
                if (dish.id == dishId) {
                    dish.copy(recipes = dish.recipes.filter { it.id != recipeId })
                } else dish
            }
        }
    }
}
