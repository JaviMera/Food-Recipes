package com.example.foodrecipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Query("SELECT * FROM recipe ORDER BY id ASC")
    fun  getRecipe() : Flow<List<RecipeEntity>>
}