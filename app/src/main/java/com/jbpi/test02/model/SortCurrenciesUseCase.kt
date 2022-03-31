package com.jbpi.test02.model

import com.jbpi.test02.model.data.CurrencyInfo
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SortCurrenciesUseCase @Inject constructor() {
    suspend fun sortAlphabetically(currencies: List<CurrencyInfo>) =
        withContext(Dispatchers.Default) {
            currencies.sortedBy { it.name }
        }

    suspend fun sortReverseAlphabetically(currencies: List<CurrencyInfo>) =
        withContext(Dispatchers.Default) {
            currencies.sortedByDescending { it.name }
        }
}
