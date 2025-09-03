package com.weatherapp.service

import com.google.gson.Gson
import com.weatherapp.model.Config
import java.io.InputStream

/**
 * Service responsible for loading application configuration.
 * Uses dependency injection for resource stream, making it testable.
 */
class ConfigService(private val configStreamProvider: () -> InputStream?) {

    /**
     * Load configuration from the provided stream.
     * Throws IllegalStateException if config is missing or invalid.
     */
    fun loadConfig(): Config {
        val stream = configStreamProvider()
            ?: throw IllegalStateException("Configuration resource not found")

        val config = Gson().fromJson(stream.reader(), Config::class.java)
        val apiKey = System.getenv("WEATHER_API_KEY") ?: config.apiKey

        if (apiKey.isBlank()) {
            throw IllegalStateException("API key not set in environment or config")
        }

        return config.copy(apiKey = apiKey)
    }

    companion object {
        /**
         * Default production instance reading appsettings.json from resources
         */
        fun default(): ConfigService {
            return ConfigService {
                ConfigService::class.java.classLoader.getResourceAsStream("appsettings.json")
            }
        }
    }
}
