package com.example.foodrecipes.data.network

import com.example.foodrecipes.models.recipes.FoodRecipe
import com.example.foodrecipes.models.recipes.Result
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

    suspend fun getRecipe(id: Int, queries: Map<String, String>) : Response<Result> {
        return foodRecipesApi.getRecipe(id, queries)
    }
}