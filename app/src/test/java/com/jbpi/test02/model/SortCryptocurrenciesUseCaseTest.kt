package com.jbpi.test02.model

import com.jbpi.test02.utils.createCurrencyListSortedAlphabetically
import com.jbpi.test02.utils.createCurrencyListSortedReverseAlphabetically
import com.jbpi.test02.utils.createUnsortedCurrencyList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SortCurrenciesUseCaseTest {

    private lateinit var sortCurrenciesUseCase: SortCurrenciesUseCase

    @Before
    fun setUp() {
        sortCurrenciesUseCase = SortCurrenciesUseCase()
    }

    @Test
    fun `test sorting alphabetically`() = runTest {
        // Given
        val unsortedCurrencies = createUnsortedCurrencyList()

        // When
        val sortedCurrencies = sortCurrenciesUseCase.sortAlphabetically(unsortedCurrencies)

        // Then
        val expectedSortedCurrencies = createCurrencyListSortedAlphabetically()
        Assert.assertEquals(expectedSortedCurrencies, sortedCurrencies)
    }

    @Test
    fun `test sorting reverse-alphabetically`() = runTest {
        // Given
        val unsortedCurrencies = createUnsortedCurrencyList()

        // When
        val sortedCurrencies = sortCurrenciesUseCase.sortReverseAlphabetically(unsortedCurrencies)

        // Then
        val expectedSortedCurrencies = createCurrencyListSortedReverseAlphabetically()
        Assert.assertEquals(expectedSortedCurrencies, sortedCurrencies)
    }
}
