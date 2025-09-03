package com.weatherapp

import com.weatherapp.service.ConfigService
import com.weatherapp.service.WeatherFetcher
import com.weatherapp.service.WeatherReportService

/**
 * Main entry point for the Weather Report Application.
 */
fun main() {
    try {
        // Load config
        val configService = ConfigService.default()
        val config = configService.loadConfig()

        // Create fetcher and report service
        val fetcher = WeatherFetcher.createDefault()
        val reportService = WeatherReportService(fetcher)

        // Generate full report for the configured day range
        val report = reportService.generateReports(
            apiKey = config.apiKey,
            cities = config.cities,
            startDay = config.targetDayRangeStart,
            endDay = config.targetDayRangeEnd
        )

        // Print the formatted tables
        println(report)

    } catch (e: Exception) {
        println("An error occurred: ${e.message}")
    }
}
