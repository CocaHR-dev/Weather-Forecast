package com.weatherapp.service

import com.weatherapp.util.formatTable

class WeatherReportService(
    private val fetcher: WeatherFetcher
) {
    fun generateReport(apiKey: String, cities: List<String>): String {
        // Generate dynamic headers for 24 hours
        val hourlyHeaders = (0 until 24).flatMap { h ->
            val label = "%02d:00".format(h)
            listOf("$label Hum", "$label Wind")
        }

        val headers = listOf("City", "MinTemp", "MaxTemp") + hourlyHeaders

        val tableData = cities.map { fetchCityForecast(it, apiKey) }

        return formatTable(headers, tableData)
    }

    private fun fetchCityForecast(city: String, apiKey: String): List<String> {
        val forecastDay =
            fetcher.getNextDayForecast(city, apiKey)?.forecast?.forecastday?.getOrNull(1)

        return if (forecastDay != null) {
            val base = listOf(
                city,
                "${forecastDay.day.minTempC}°C",
                "${forecastDay.day.maxTempC}°C"
            )

            val hourlyValues = forecastDay.hour.take(24).flatMap { hour ->
                listOf(
                    "${hour.humidity}%",
                    "${hour.windSpeed} km/h ${hour.windDir}"
                )
            }

            base + hourlyValues
        } else {
            listOf(city, "Error", "Error") +
                    List(24 * 2) { "Error" }
        }
    }
}