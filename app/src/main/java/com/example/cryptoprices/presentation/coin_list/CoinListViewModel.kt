package com.example.cryptoprices.presentation.coin_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoprices.common.Resource
import com.example.cryptoprices.domain.use_case.get_coin_list.GetCoinsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinsListUseCase: GetCoinsListUseCase
) : ViewModel(){

    // Don't want to modify the state in composables, so we pass a public immutable state
    // and keep the private one mutable in the ViewModel only
    private val _state = mutableStateOf<CoinListState>(CoinListState())
    val state: State<CoinListState> = _state

    init {
        getCoins()
    }

    fun getCoins() {
        // since the invoke method is overloaded with the operator keyword, we can call the class GetCoinsList like a method
        getCoinsListUseCase().onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = CoinListState(coins = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = CoinListState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = CoinListState(error = result.message ?: "An error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }
}