package com.example.foodrecipes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.models.recipes.FoodRecipe
import com.example.foodrecipes.util.Constants.Companion.RECIPE_TABLE

@Entity(tableName = RECIPE_TABLE)
data class RecipeEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}