package com.example.cryptoprices.data.remote.response.coin

import com.example.cryptoprices.data.repository.CoinDto

data class Coin(
    val ath: Double,
    val ath_change_percentage: Double,
    val ath_date: String,
    val atl: Double,
    val atl_change_percentage: Double,
    val atl_date: String,
    val circulating_supply: Double,
    val current_price: Double,
    val fully_diluted_valuation: Double,
    val high_24h: Double,
    val id: String,
    val image: String,
    val last_updated: String,
    val low_24h: Double,
    val market_cap: Double,
    val market_cap_change_24h: Double,
    val market_cap_change_percentage_24h: Double,
    val market_cap_rank: Int,
    val max_supply: Double,
    val name: String,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
    val price_change_percentage_24h_in_currency: Double,
    val roi: Any,
    val sparkline_in_7d: SparklineIn7d,
    val symbol: String,
    val total_supply: Double,
    val total_volume: Double
)

fun Coin.toCoinDto(): CoinDto {
    return CoinDto(
        current_price = current_price,
        id = id,
        image = image,
        market_cap_rank = market_cap_rank,
        name = name,
        price_change_percentage_24h = price_change_percentage_24h,
        symbol = symbol
    )
}