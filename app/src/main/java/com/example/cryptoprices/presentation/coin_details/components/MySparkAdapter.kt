package com.example.cryptoprices.presentation.coin_details.components

import com.robinhood.spark.SparkAdapter

class MySparkAdapter(private val prices: List<Double>) : SparkAdapter() {

    override fun getCount(): Int {
        return prices.size
    }

    override fun getItem(index: Int): Any {
        return prices[index]
    }

    override fun getY(index: Int): Float {
        return prices[index].toFloat()
    }

    override fun hasBaseLine(): Boolean {
        return true
    }

    override fun getBaseLine(): Float {
        return prices.last().toFloat()
    }

}

