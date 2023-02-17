package com.example.cryptoprices.data.remote

import com.example.cryptoprices.data.remote.response.coin.Coin
import com.example.cryptoprices.data.remote.response.coin_details.CoinDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinGeckoAPI {

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=true&price_change_percentage=24h")
    suspend fun getCoins(): List<Coin>

    @GET("coins/{coinId}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=true")
    suspend fun getCoinById(@Path("coinId") coinId: String): CoinDetails

}