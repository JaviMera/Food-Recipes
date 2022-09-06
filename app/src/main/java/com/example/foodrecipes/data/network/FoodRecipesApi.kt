package com.example.foodrecipes.data.network

import com.example.foodrecipes.models.recipes.FoodRecipe
import com.example.foodrecipes.models.recipes.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ) : Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ) : Response<FoodRecipe>

    @GET("/recipes/{id}/information")
    suspend fun getRecipe(
        @Path("id") id: Int,
        @QueryMap queries: Map<String, String>
    ) : Response<Result>
}