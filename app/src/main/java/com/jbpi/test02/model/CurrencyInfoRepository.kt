package com.jbpi.test02.model

import com.jbpi.test02.model.data.CurrencyInfo
import com.jbpi.test02.model.db.CurrencyInfoDao
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyInfoRepository @Inject constructor(
    private val databaseHandle: CurrencyInfoDao
) {

    suspend fun getCurrenciesData(): List<CurrencyInfo> = withContext(Dispatchers.IO) {
        databaseHandle.getAll()
    }

    suspend fun downloadAndPersistCurrenciesData() = withContext(Dispatchers.IO) {
        val currencies = pretendToDownloadDataFromApi()
        databaseHandle.insertAll(currencies)
    }

    private fun pretendToDownloadDataFromApi(): List<CurrencyInfo> =
        listOf(
            CurrencyInfo(
                id = "BTC",
                name = "Bitcoin",
                symbol = "BTC"
            ),
            CurrencyInfo(
                id = "ETH",
                name = "Ethereum",
                symbol = "ETH"
            ),
            CurrencyInfo(
                id = "XRP",
                name = "XRP",
                symbol = "XRP"
            ),
            CurrencyInfo(
                id = "BCH",
                name = "Bitcoin Cash",
                symbol = "BCH"
            ),
            CurrencyInfo(
                id = "LTC",
                name = "Litecoin",
                symbol = "LTC"
            ),
            CurrencyInfo(
                id = "EOS",
                name = "EOS",
                symbol = "EOS"
            ),
            CurrencyInfo(
                id = "BNB",
                name = "Binance Coin",
                symbol = "BNB"
            ),
            CurrencyInfo(
                id = "LINK",
                name = "Chainlink",
                symbol = "LINK"
            ),
            CurrencyInfo(
                id = "NEO",
                name = "NEO",
                symbol = "NEO"
            ),
            CurrencyInfo(
                id = "ETC",
                name = "Ethereum Classic",
                symbol = "ETC"
            ),
            CurrencyInfo(
                id = "ONT",
                name = "Ontology",
                symbol = "ONT"
            ),
            CurrencyInfo(
                id = "CRO",
                name = "Crypto.com Chain",
                symbol = "CRO"
            ),
            CurrencyInfo(
                id = "CUC",
                name = "Cucumber",
                symbol = "CUC"
            ),
            CurrencyInfo(
                id = "USDC",
                name = "USD Coin",
                symbol = "USDC"
            )
        )
}
