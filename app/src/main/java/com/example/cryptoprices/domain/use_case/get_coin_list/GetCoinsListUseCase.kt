package com.example.cryptoprices.domain.use_case.get_coin_list

import com.example.cryptoprices.common.Resource
import com.example.cryptoprices.data.remote.response.coin.toCoinDto
import com.example.cryptoprices.data.repository.CoinDto
import com.example.cryptoprices.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinsListUseCase @Inject constructor(
    private val repository: CoinRepository
){

    // Uses the Resource class defined in the common package
    // operator overloads the default invoke function
    operator fun invoke(): Flow<Resource<List<CoinDto>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().map { it.toCoinDto() }
            emit(Resource.Success(coins))
        } catch (e: HttpException) {
            // There was an error, so the response is not 2XX
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
        }
    }

}