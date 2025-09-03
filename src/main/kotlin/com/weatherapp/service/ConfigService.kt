package com.weatherapp.service

import com.google.gson.Gson
import com.weatherapp.model.Config

class ConfigService(private val resourceName: String = "appsettings.json") {

    fun loadConfig(): Config {
        val stream = javaClass.classLoader.getResourceAsStream(resourceName)
            ?: throw IllegalStateException("Configuration resource not found: $resourceName")

        val config = Gson().fromJson(stream.reader(), Config::class.java)
        val apiKey = System.getenv("WEATHER_API_KEY") ?: config.apiKey
        if (apiKey.isBlank()) throw IllegalStateException("API key not set in environment or config")

        return config.copy(apiKey = apiKey)
    }
}