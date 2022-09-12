package com.example.foodrecipes.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodrecipes.models.recipes.Result

@Entity(tableName = "favorite")
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)