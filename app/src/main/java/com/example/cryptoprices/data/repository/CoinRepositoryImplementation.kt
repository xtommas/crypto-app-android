package com.example.cryptoprices.data.repository

import com.example.cryptoprices.data.remote.CoinGeckoAPI
import com.example.cryptoprices.data.remote.response.coin.Coin
import com.example.cryptoprices.data.remote.response.coin_details.CoinDetails
import com.example.cryptoprices.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImplementation @Inject constructor(
    private val api: CoinGeckoAPI
) : CoinRepository {

    override suspend fun getCoins(): List<Coin> {
        return api.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetails {
        return api.getCoinById(coinId)
    }
}