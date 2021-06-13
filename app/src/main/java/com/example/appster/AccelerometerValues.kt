package com.example.appster

data class AccelerometerValues(
    val uid : String,
    val xaccel: String? = null,
    val yaccel: String? = null,
    val zaccel: String? = null) {
}