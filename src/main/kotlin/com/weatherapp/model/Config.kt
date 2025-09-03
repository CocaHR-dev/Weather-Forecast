package com.weatherapp.model

data class Config(
    val apiKey: String,
    val cities: List<String>
)