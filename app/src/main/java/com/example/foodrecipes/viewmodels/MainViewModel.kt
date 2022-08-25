package com.example.foodrecipes.viewmodels

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.foodrecipes.data.Repository
import com.example.foodrecipes.data.local.RecipeEntity
import com.example.foodrecipes.models.recipes.FoodRecipe
import com.example.foodrecipes.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val connectivityManager: ConnectivityManager
) : ViewModel() {

    // Room Database
    val readRecipes: LiveData<List<RecipeEntity>> = repository.local.getRecipes().asLiveData()

    private fun insertRecipe(recipeEntity: RecipeEntity) {
        viewModelScope.launch(Dispatchers.IO){
            repository.local.insertRecipe(recipeEntity)
        }
    }

    // Retrofit
    var recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        recipesResponse.value = NetworkResult.Loading()

        if(hasInternetConnection()){
            try {
                val response = repository.remote.getRecipes(queries)
                recipesResponse.value = handleFoodRecipesResponse(response)

                val foodRecipe = recipesResponse.value!!.data
                if(foodRecipe != null){
                    offlineCacheRecipe(foodRecipe)
                }
            } catch (e: Exception){
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else{
            recipesResponse.value = NetworkResult.Error("No Internet connection")
        }
    }

    private fun offlineCacheRecipe(foodRecipe: FoodRecipe) {
        val recipeEntity = RecipeEntity(foodRecipe)
        insertRecipe(recipeEntity)
    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        return when{
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited")
            }
            response.body()!!.results.isEmpty() -> NetworkResult.Error("Recipes not found.")
            response.isSuccessful -> NetworkResult.Success(response.body()!!)
            else ->{
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection() : Boolean {

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}