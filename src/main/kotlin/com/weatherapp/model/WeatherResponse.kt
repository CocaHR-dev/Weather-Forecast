package com.weatherapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("forecast") val forecast: Forecast
)

data class Forecast(
    @SerializedName("forecastday") val forecastday: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: Day,
    @SerializedName("hour") val hour: List<Hour>
)

data class Day(
    @SerializedName("mintemp_c") val minTempC: Double,
    @SerializedName("maxtemp_c") val maxTempC: Double
)

data class Hour(
    @SerializedName("humidity") val humidity: Double,
    @SerializedName("wind_kph") val windSpeed: Double,
    @SerializedName("wind_dir") val windDir: String,
)
