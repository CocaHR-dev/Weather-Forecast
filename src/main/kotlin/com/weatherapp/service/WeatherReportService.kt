package com.weatherapp.service

import com.weatherapp.util.formatTable

/**
 * Service responsible for generating weather report tables.
 * Handles multiple cities and a range of days.
 */
class WeatherReportService(private val fetcher: WeatherFetcher) {

    /**
     * Generates weather report tables for a range of days.
     * One table per day, cities as rows.
     *
     * @param apiKey WeatherAPI key
     * @param cities List of city names
     * @param startDay Start day of the range (1-based)
     * @param endDay End day of the range (inclusive)
     * @return Formatted string with all tables
     */
    fun generateReports(apiKey: String, cities: List<String>, startDay: Int, endDay: Int): String {
        val sb = StringBuilder()

        // Fetch all forecasts once per city (up to endDay)
        val forecastsPerCity = cities.mapNotNull { city ->
            fetcher.getForecast(city, apiKey, days = endDay)?.let { city to it }
        }

        if (forecastsPerCity.isEmpty()) return "No data available"

        // Loop over the day range
        for (dayIndex in (startDay - 1) until endDay) {
            // Use the date from the API for the table caption
            val date = forecastsPerCity.first().second.forecast.forecastday.getOrNull(dayIndex)?.date
                ?: continue
            sb.appendLine("=== Weather report for $date ===")

            // Prepare table rows: one row per city
            val tableData = forecastsPerCity.map { (city, weatherResponse) ->
                val forecastDay = weatherResponse.forecast.forecastday.getOrNull(dayIndex)
                if (forecastDay == null) return@map listOf(city) + List(2 + 24 * 2) { "Error" }

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
            }

            // Create headers
            val hourlyHeaders = (0 until 24).flatMap { h ->
                val label = "%02d:00".format(h)
                listOf("$label Hum", "$label Wind")
            }
            val headers = listOf("City", "MinTemp", "MaxTemp") + hourlyHeaders

            sb.appendLine(formatTable(headers, tableData))
            sb.appendLine()
        }

        return sb.toString()
    }
}
