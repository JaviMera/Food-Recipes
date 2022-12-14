package com.example.foodrecipes.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.foodrecipes.data.local.entities.FavoriteEntity
import com.example.foodrecipes.data.local.entities.FoodJokeEntity
import com.example.foodrecipes.data.local.entities.RecipeEntity
import com.example.foodrecipes.models.recipes.FoodJoke
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface RecipeDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM recipe ORDER BY id ASC")
    fun  getRecipes() : Flow<List<RecipeEntity>>

    @Query("SELECT * FROM favorite ORDER BY id ASC")
    fun getFavoriteRecipes() : Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite")
    suspend fun deleteAllFavoriteRecipes()

    @Insert(onConflict = REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke ORDER BY id ASC")
    fun getFoodJoke() : Flow<List<FoodJokeEntity>>
}