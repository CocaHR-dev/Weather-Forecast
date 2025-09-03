package com.weatherapp.api

import com.weatherapp.model.WeatherResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.google.gson.GsonBuilder

/**
 * Retrofit API interface for WeatherAPI.com.
 * Only this package knows about Retrofit, keeping the rest of the code clean.
 */
interface WeatherApiService {

    /**
     * Fetch weather forecast for a city.
     * @param apiKey API key for authentication
     * @param city City name
     * @param days Number of forecast days
     * @return Call wrapping WeatherResponse
     */
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
         * Factory to create a real API service instance.
         */
        fun create(): WeatherApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}
