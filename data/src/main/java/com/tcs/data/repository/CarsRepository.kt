package com.tcs.data.repository

import com.tcs.data.api.ApiService
import com.tcs.data.model.CarSearchResponseItem
import dagger.hilt.android.scopes.ActivityRetainedScoped

import javax.inject.Inject

@ActivityRetainedScoped
class CarsRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getCars(): List<CarSearchResponseItem> = apiService.getCars()

}