package com.example.cryptoprices.data.remote.response.coin_details

import com.example.cryptoprices.data.repository.CoinDetailsDto

data class CoinDetails(
    val additional_notices: List<Any>,
    val asset_platform_id: Any,
    val block_time_in_minutes: Int,
    val categories: List<String>,
    val coingecko_rank: Int,
    val coingecko_score: Double,
    val community_score: Double,
    val country_origin: String,
    val description: Description,
    val detail_platforms: DetailPlatforms,
    val developer_score: Double,
    val genesis_date: String,
    val hashing_algorithm: String,
    val ico_data: IcoData,
    val id: String,
    val image: Image,
    val last_updated: String,
    val links: LinksX,
    val liquidity_score: Double,
    val market_cap_rank: Int,
    val market_data: MarketData,
    val name: String,
    val platforms: Platforms,
    val public_interest_score: Double,
    val public_interest_stats: PublicInterestStats,
    val public_notice: Any,
    val sentiment_votes_down_percentage: Double,
    val sentiment_votes_up_percentage: Double,
    val status_updates: List<Any>,
    val symbol: String
)

fun CoinDetails.toCoinDetailsDto(): CoinDetailsDto {
    return CoinDetailsDto(
        description = description,
        genesis_date = genesis_date,
        id = id,
        image = image,
        links = links,
        market_cap_rank = market_cap_rank,
        market_data = market_data,
        name = name,
        symbol = symbol
    )
}