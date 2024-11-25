package com.tcs.data.api

import com.tcs.data.model.CarSearchResponseItem
import retrofit2.http.*

interface ApiService {

    @GET("/")
    suspend fun getCars(): List<CarSearchResponseItem>
}
