package com.example.cryptoprices.data.remote.response.coin_details

data class Roi(
    val currency: String,
    val percentage: Double,
    val times: Double
)