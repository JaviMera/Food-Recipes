package com.example.foodrecipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodrecipes.data.local.entities.FavoriteEntity
import com.example.foodrecipes.data.local.entities.RecipeEntity

@Database(
    entities = [RecipeEntity::class, FavoriteEntity::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(RecipeTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}