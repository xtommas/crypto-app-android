package com.example.cryptoprices.domain.use_case.get_coin

import com.example.cryptoprices.common.Resource
import com.example.cryptoprices.data.remote.response.coin_details.toCoinDetailsDto
import com.example.cryptoprices.data.repository.CoinDetailsDto
import com.example.cryptoprices.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(
    private val repository: CoinRepository
){

    // Uses the Resource class defined in the common package
    operator fun invoke(coinId: String): Flow<Resource<CoinDetailsDto>> = flow {
        try {
            emit(Resource.Loading())
            val coin = repository.getCoinById(coinId).toCoinDetailsDto()
            emit(Resource.Success(coin))
        } catch (e: HttpException) {
            // There was an error, so the response is not 2XX
            emit(Resource.Error(e.localizedMessage ?: "An error occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
        }
    }

}