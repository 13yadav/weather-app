package com.strangecoder.weather.network

import com.strangecoder.weather.Constants.API_KEY
import com.strangecoder.weather.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getWeatherInfo(
        @Query("lat") latitude: Double = 30.9,
        @Query("lon") longitude: Double = 75.85,
        @Query("appid") apiKey: String = API_KEY
    ): WeatherResponse
}