package com.strangecoder.weather.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("visibility")
    val visibility: Double? = null,

    @SerializedName("timezone")
    val timezone: Double? = null,

    @SerializedName("main")
    val main: Main? = null,

    @SerializedName("clouds")
    val clouds: Clouds? = null,

    @SerializedName("sys")
    val sys: Sys? = null,

    @SerializedName("dt")
    val dt: Double? = null,

    @SerializedName("coord")
    val coord: Coord? = null,

    @SerializedName("weather")
    val weather: List<WeatherItem?>? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("cod")
    val cod: Double? = null,

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("base")
    val base: String? = null,

    @SerializedName("wind")
    val wind: Wind? = null
)

data class WeatherItem(

    @SerializedName("icon")
    val icon: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("main")
    val main: String? = null,

    @SerializedName("id")
    val id: Int? = null
)

data class Wind(

    @SerializedName("deg")
    val deg: Double? = null,

    @SerializedName("speed")
    val speed: Double? = null,

    @SerializedName("gust")
    val gust: Double? = null
)

data class Clouds(

    @SerializedName("all")
    val all: Double? = null
)

data class Sys(

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("sunrise")
    val sunrise: Double? = null,

    @SerializedName("sunset")
    val sunset: Double? = null,

    @SerializedName("id")
    val id: Double? = null,

    @SerializedName("type")
    val type: Double? = null
)

data class Main(

    @SerializedName("temp")
    val temp: Double? = null,

    @SerializedName("temp_min")
    val tempMin: Double? = null,

    @SerializedName("humidity")
    val humidity: Double? = null,

    @SerializedName("pressure")
    val pressure: Double? = null,

    @SerializedName("feels_like")
    val feelsLike: Double? = null,

    @SerializedName("temp_max")
    val tempMax: Double? = null
)

data class Coord(

    @SerializedName("lon")
    val lon: Double? = null,

    @SerializedName("lat")
    val lat: Double? = null
)
