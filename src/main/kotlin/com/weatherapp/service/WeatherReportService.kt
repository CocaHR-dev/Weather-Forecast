package com.weatherapp.service

import com.weatherapp.util.formatTable

class WeatherReportService(
    private val fetcher: WeatherFetcher
) {

    fun generateReport(apiKey: String, cities: List<String>): String {
        val tableData = cities.map { fetchCityForecast(it, apiKey) }
        val headers = listOf("City", "MinTemp", "MaxTemp", "Humidity", "WindSpeed")
        return formatTable(headers, tableData)
    }

    private fun fetchCityForecast(city: String, apiKey: String): List<String> {
        val forecast = fetcher.getNextDayForecast(city, apiKey)?.forecast?.forecastday?.get(1) //selects tomorrow explicitly
        return if (forecast != null) {
            listOf(
                city,
                "${forecast.day.minTempC}°C",
                "${forecast.day.maxTempC}°C",
                "${forecast.day.avgHumidity}%",
                "${forecast.day.maxWindKph} kph"
            )
        } else {
            listOf(city, "Error", "Error", "Error", "Error", "Error")
        }
    }
}