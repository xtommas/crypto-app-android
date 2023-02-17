package com.example.cryptoprices.presentation.coin_details

import com.example.cryptoprices.data.repository.CoinDetailsDto

data class CoinDetailsState(
    val isLoading: Boolean = false,
    val coin: CoinDetailsDto? = null,
    val error: String = ""
)
