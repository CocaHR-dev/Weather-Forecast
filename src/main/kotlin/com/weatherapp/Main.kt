package com.weatherapp

import com.weatherapp.service.ConfigService
import com.weatherapp.service.WeatherFetcher
import com.weatherapp.service.WeatherReportService

fun main() {
    val configService = ConfigService()
    val config = configService.loadConfig()

    val fetcher = WeatherFetcher()
    val reportService = WeatherReportService(fetcher)

    println(reportService.generateReport(config.apiKey, config.cities))
}