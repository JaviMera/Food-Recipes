package com.example.foodrecipes.util

class Constants {

    companion object {
        const val API_KEY = "9d92e45a9ef04242b026b12932f4d781"
        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"
        const val QUERY_SEARCH="query"

        // Room database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPE_TABLE = "recipe"
        const val FAVORITE_TABLE = "favorite"
        const val FOOD_JOKE_TABLE = "food_joke"

        // Bottom sheet preferences
        const val DEFAULT_MEAL_TYPE = "main course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_NAME = "food_name"

        const val PREFERENCES_BACK_ONLINE = "backOnline"

        const val RECIPE_RESULT_KEY = "recipeBundle"
    }
}