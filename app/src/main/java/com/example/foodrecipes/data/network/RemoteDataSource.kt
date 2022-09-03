package com.example.foodrecipes.data.network

import com.example.foodrecipes.data.network.FoodRecipesApi
import com.example.foodrecipes.models.recipes.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor (
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getRecipes(queries: Map<String, String>) : Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun searchRecipes(queries: Map<String, String>) : Response<FoodRecipe>{
        return foodRecipesApi.searchRecipes(queries);
    }
}