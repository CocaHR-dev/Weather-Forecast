package com.weatherapp.api

import com.google.gson.GsonBuilder
import com.weatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/forecast.json")
    fun getForecast(
        @Query("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Call<WeatherResponse>

    companion object {
        /**
         * Factory to provide a default Retrofit implementation
         */
        fun create(baseUrl: String = "https://api.weatherapi.com/"): WeatherApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

            return retrofit.create(WeatherApiService::class.java)
        }
    }
}
