package com.weatherapp.service

import com.weatherapp.api.WeatherApiService
import com.weatherapp.model.WeatherResponse

/**
 * Service responsible for fetching weather data.
 * Fully decoupled from Retrofit; depends only on WeatherApiService interface.
 */
class WeatherFetcher(private val apiService: WeatherApiService) {

    /**
     * Fetch forecast for a city.
     *
     * @param city the city name
     * @param apiKey WeatherAPI key
     * @param days number of days to fetch
     * @return WeatherResponse or null if the call fails
     */
    fun getForecast(city: String, apiKey: String, days: Int): WeatherResponse? {
        val response = apiService.getForecast(apiKey, city, days).execute()
        return if (response.isSuccessful) response.body() else null
    }
}
