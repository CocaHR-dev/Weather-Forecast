package com.weatherapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

import com.weatherapp.model.WeatherResponse

interface WeatherApiService {
    @GET("v1/forecast.json")
    fun getTomorrowForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int = 2, // 2 is tomorrow
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Call<WeatherResponse>
}