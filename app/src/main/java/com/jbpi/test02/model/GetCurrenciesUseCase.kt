package com.jbpi.test02.model

import com.jbpi.test02.model.data.CurrencyInfo
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currencyInfoRepository: CurrencyInfoRepository
) {
    suspend operator fun invoke(): List<CurrencyInfo> {
        var currencies = currencyInfoRepository.getCurrenciesData()

        if (currencies.isEmpty()) {
            currencyInfoRepository.downloadAndPersistCurrenciesData()
            currencies = currencyInfoRepository.getCurrenciesData()
        }

        return currencies
    }
}
