package com.jbpi.test02

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.jbpi.test02.Event.LoadDataButtonPressed
import com.jbpi.test02.Event.SortCurrenciesButtonPressed
import com.jbpi.test02.Reaction.CurrenciesDatasetUpdated
import com.jbpi.test02.SortType.ALPHABETICAL
import com.jbpi.test02.SortType.NONE
import com.jbpi.test02.SortType.REVERSE_ALPHABETICAL
import com.jbpi.test02.model.GetCurrenciesUseCase
import com.jbpi.test02.model.SortCurrenciesUseCase
import com.jbpi.test02.model.data.CurrencyInfo
import javax.inject.Inject
import kotlinx.coroutines.launch

class DemoViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val sortCurrenciesUseCase: SortCurrenciesUseCase
) : ViewModel() {

    private var _reaction = MutableLiveData<Reaction>()
    val reaction: LiveData<Reaction>
        get() = _reaction

    private var currencies: List<CurrencyInfo> = emptyList()
    private var currentSortType = NONE

    fun dispatchEvent(event: Event) {
        when (event) {
            LoadDataButtonPressed -> {
                viewModelScope.launch {
                    currencies = getCurrenciesUseCase()
                    currentSortType = NONE
                    _reaction.postValue(CurrenciesDatasetUpdated(currencies, true))
                }
            }
            SortCurrenciesButtonPressed -> {
                viewModelScope.launch {
                    sortCurrencies()
                    _reaction.postValue(CurrenciesDatasetUpdated(currencies, true))
                }
            }
        }
    }

    private suspend fun sortCurrencies() {
        when (currentSortType) {
            NONE, REVERSE_ALPHABETICAL -> {
                currencies = sortCurrenciesUseCase.sortAlphabetically(currencies)
                currentSortType = ALPHABETICAL
            }
            else -> {
                currencies = sortCurrenciesUseCase.sortReverseAlphabetically(currencies)
                currentSortType = REVERSE_ALPHABETICAL
            }
        }
    }
}

private enum class SortType {
    NONE, ALPHABETICAL, REVERSE_ALPHABETICAL
}

class DemoViewModelFactory @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val sortCurrenciesUseCase: SortCurrenciesUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        DemoViewModel(
            getCurrenciesUseCase = getCurrenciesUseCase,
            sortCurrenciesUseCase = sortCurrenciesUseCase
        ) as T
}
