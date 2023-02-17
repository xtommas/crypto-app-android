package com.example.cryptoprices.data.repository

import com.example.cryptoprices.data.remote.response.coin_details.*

data class CoinDetailsDto(
    val description: Description,
    val genesis_date: String?,
    val id: String,
    val image: Image,
    val links: LinksX,
    val market_cap_rank: Int,
    val market_data: MarketData,
    val name: String,
    val symbol: String
)
