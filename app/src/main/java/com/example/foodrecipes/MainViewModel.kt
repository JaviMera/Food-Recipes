package com.example.foodrecipes

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodrecipes.data.Repository
import com.example.foodrecipes.models.recipes.FoodRecipe
import com.example.foodrecipes.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

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
            } catch (e: Exception){
                recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else{
            recipesResponse.value = NetworkResult.Error("No Internet connection")
        }
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
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

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