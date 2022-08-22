package com.example.foodrecipes.data

import com.example.foodrecipes.data.network.RemoteDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    val remote = remoteDataSource
}