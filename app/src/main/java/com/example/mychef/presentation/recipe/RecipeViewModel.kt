package com.example.mychef.presentation.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mychef.domain.RecipeRepository
import com.example.mychef.model.nutrition.NutritionInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState

    fun setSelectedNutritionInfo(nutritionInfo: NutritionInfo?) {
        _uiState.update { it.copy(recipeNutrients = nutritionInfo) }
    }

    fun getRecipeNutrients(): NutritionInfo? {
        return uiState.value.recipeNutrients
    }

    fun loadRecipeDetail(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, isSuccess = false) }
            try {
                val recipe = repository.getRecipeDetail(recipeId)
                _uiState.update { it.copy(selectedRecipe = recipe, isLoading = false, isSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false, isSuccess = false) }
            }
        }
    }

    fun loadRecipeByCategory(category: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val recipes = repository.getRecipesByCategory(category)
                _uiState.update { it.copy(recipesByCategory = recipes, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun loadRecipeNutrients(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val nutrients = repository.getRecipeNutrients(recipeId)
                _uiState.update { it.copy(recipeNutrients = nutrients, isLoading = false) }
            } catch(e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}