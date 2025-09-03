package com.weatherapp

import com.weatherapp.api.WeatherApiService
import com.weatherapp.service.ConfigService
import com.weatherapp.service.WeatherFetcher
import com.weatherapp.service.WeatherReportService

fun main() {
    // Load config (supports environment variable override)
    val configService = ConfigService.default()
    val config = configService.loadConfig()

    // Create API service (Retrofit encapsulated)
    val apiService = WeatherApiService.create()

    // Inject API service into fetcher
    val fetcher = WeatherFetcher(apiService)

    // Inject fetcher into report service
    val reportService = WeatherReportService(fetcher)

    // Generate the full report for the configured date range
    val report = reportService.generateReport(
        apiKey = config.apiKey,
        cities = config.cities,
        startDay = config.targetDayRangeStart,
        endDay = config.targetDayRangeEnd
    )

    // Print the report (tables are automatically captioned by date)
    println(report)
}
