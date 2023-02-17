package com.example.cryptoprices.di

import com.example.cryptoprices.common.Constants
import com.example.cryptoprices.data.remote.CoinGeckoAPI
import com.example.cryptoprices.data.repository.CoinRepositoryImplementation
import com.example.cryptoprices.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Components live as long as the app
object AppModule {

    /* Dependency Injection makes dependencies (objects representing APIs, Databases, etc.)
    replaceable, which makes it easier to replace the current repository with, for example, a test repository */

    @Provides
    @Singleton
    fun provideCoinGeckoAPI(): CoinGeckoAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinGeckoAPI): CoinRepository {
        return CoinRepositoryImplementation(api)
    }

}