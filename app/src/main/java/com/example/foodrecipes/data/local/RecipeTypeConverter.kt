package com.example.foodrecipes.data.local

import androidx.room.TypeConverter
import com.example.foodrecipes.models.recipes.FoodRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe) : String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String) : FoodRecipe {
        val listType = object: TypeToken<FoodRecipe>(){}.type
        return gson.fromJson(data, listType)
    }
}