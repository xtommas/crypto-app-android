package com.example.cryptoprices.data.repository

data class CoinDto(
    val current_price: Double,
    val id: String,
    val image: String,
    val market_cap_rank: Int,
    val name: String,
    val price_change_percentage_24h: Double,
    val symbol: String
)
