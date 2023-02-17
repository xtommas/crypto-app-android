package com.example.cryptoprices.domain.repository

import com.example.cryptoprices.data.remote.response.coin.Coin
import com.example.cryptoprices.data.remote.response.coin_details.CoinDetails

interface CoinRepository {

    suspend fun getCoins(): List<Coin>

    suspend fun getCoinById(coinId: String) : CoinDetails
}