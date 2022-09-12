package com.example.foodrecipes.data

import com.example.foodrecipes.data.local.RecipeDao
import com.example.foodrecipes.data.local.entities.FavoriteEntity
import com.example.foodrecipes.data.local.entities.RecipeEntity
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

    fun getFavoriteRecipes() : Flow<List<FavoriteEntity>> {
        return recipesDao.getFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipes(favoriteEntity: FavoriteEntity) {
        recipesDao.insertFavoriteRecipes(favoriteEntity)
    }

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        recipesDao.deleteFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes(){
        recipesDao.deleteAllFavoriteRecipes()
    }
}