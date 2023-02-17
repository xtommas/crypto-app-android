package com.example.cryptoprices.presentation.coin_list

import com.example.cryptoprices.data.repository.CoinDto

data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinDto> = emptyList(),
    val error: String = ""
)
