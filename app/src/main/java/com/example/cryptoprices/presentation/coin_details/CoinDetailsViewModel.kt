package com.example.cryptoprices.presentation.coin_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoprices.common.Constants
import com.example.cryptoprices.common.Resource
import com.example.cryptoprices.domain.use_case.get_coin.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    // Don't want to modify the state in composables, so we pass a public immutable state
    // and keep the private one mutable in the ViewModel only
    private val _state = mutableStateOf<CoinDetailsState>(CoinDetailsState())
    val state: State<CoinDetailsState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
            getCoin(coinId)
        }
    }

    private fun getCoin(coinId: String) {
        // since the invoke method is overloaded with the operator keyword, we can call the class GetCoinsList like a method
        getCoinUseCase(coinId).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = CoinDetailsState(coin = result.data)
                }
                is Resource.Loading -> {
                    _state.value = CoinDetailsState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = CoinDetailsState(error = result.message ?: "An error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}