package com.kujira.homestay.data.model.response

class Coord {
    var lon = 0.0
    var lat = 0.0
}

class Weather {
    var id = 0
    var main: String? = null
    var description: String? = null
    var icon: String? = null
}

class Main {
    var temp = 0.0
    var feels_like = 0.0
    var temp_min = 0.0
    var temp_max = 0.0
    var pressure = 0
    var humidity = 0
    var sea_level = 0
    var grnd_level = 0
}

class Wind {
    var speed = 0.0
    var deg = 0
    var gust = 0.0
}

class Clouds {
    var all = 0
}

class Sys {
    var type = 0
    var id = 0
    var country: String? = null
    var sunrise = 0
    var sunset = 0
}

class Root {
    var coord: Coord? = null
    var weather: List<Weather>? = null
    var base: String? = null
    var main: Main? = null
    var visibility = 0
    var wind: Wind? = null
    var clouds: Clouds? = null
    var dt = 0
    var sys: Sys? = null
    var timezone = 0
    var id = 0
    var name: String? = null
    var cod = 0
}
