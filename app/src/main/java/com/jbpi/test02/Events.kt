package com.jbpi.test02

import com.jbpi.test02.model.data.CurrencyInfo

sealed class Event {
    object LoadDataButtonPressed : Event()
    object SortCurrenciesButtonPressed : Event()
}

sealed class Reaction {
    data class CurrenciesDatasetUpdated(
        val currencies: List<CurrencyInfo>,
        val sortingEnabled: Boolean
    ) : Reaction()
}
