package com.example.foodrecipes.data

import com.example.foodrecipes.data.local.RecipeDao
import com.example.foodrecipes.data.local.RecipeEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipeDao
) {

    fun getRecipes() : Flow<List<RecipeEntity>> {
        return recipesDao.getRecipes()
    }
    suspend fun insertRecipe(recipeEntity: RecipeEntity){
        recipesDao.insertRecipe(recipeEntity)
    }
}