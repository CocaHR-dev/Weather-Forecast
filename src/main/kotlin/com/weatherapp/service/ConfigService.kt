package com.weatherapp.service

import com.google.gson.Gson
import com.weatherapp.model.Config

/**
 * Service responsible for loading configuration from a JSON resource.
 * Supports environment variable override for the API key.
 */
class ConfigService(private val resourceName: String = "appsettings.json") {

    /**
     * Loads configuration and validates it.
     * @throws IllegalStateException if the resource is missing or API key is blank
     * @throws IllegalArgumentException if startDay > endDay
     */
    fun loadConfig(): Config {
        val stream = javaClass.classLoader.getResourceAsStream(resourceName)
            ?: throw IllegalStateException("Configuration resource not found: $resourceName")

        val config = Gson().fromJson(stream.reader(), Config::class.java)

        // Use environment variable if present
        val apiKey = System.getenv("WEATHER_API_KEY") ?: config.apiKey

        if (apiKey.isBlank()) throw IllegalStateException("API key not set in environment or config")

        if (config.targetDayRangeStart > config.targetDayRangeEnd) {
            throw IllegalArgumentException(
                "Invalid day range: start (${config.targetDayRangeStart}) > end (${config.targetDayRangeEnd})"
            )
        }

        return config.copy(apiKey = apiKey)
    }

    companion object {
        fun default() = ConfigService()
    }
}
