package com.weatherapp.service

import com.google.gson.GsonBuilder
import com.weatherapp.api.WeatherApiService
import com.weatherapp.model.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherFetcher(apiBaseUrl: String = "https://api.weatherapi.com/") {

    private val service: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        service = retrofit.create(WeatherApiService::class.java)
    }

    fun getNextDayForecast(city: String, apiKey: String): WeatherResponse? {
        val response = service.getTomorrowForecast(apiKey, city).execute()
        return if (response.isSuccessful) response.body() else null
    }
}