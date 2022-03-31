package com.jbpi.test02

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jbpi.test02.Event.LoadDataButtonPressed
import com.jbpi.test02.Event.SortCurrenciesButtonPressed
import com.jbpi.test02.Reaction.CurrenciesDatasetUpdated
import com.jbpi.test02.model.GetCurrenciesUseCase
import com.jbpi.test02.model.SortCurrenciesUseCase
import com.jbpi.test02.utils.createCurrencyListSortedAlphabetically
import com.jbpi.test02.utils.createCurrencyListSortedReverseAlphabetically
import com.jbpi.test02.utils.createUnsortedCurrencyList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DemoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val standardTestDispatcher = UnconfinedTestDispatcher()

    private val getCurrenciesUseCase: GetCurrenciesUseCase = mockk(relaxed = true)
    private val sortCurrenciesUseCase: SortCurrenciesUseCase = mockk(relaxed = true)

    private lateinit var mainViewModel: DemoViewModel

    @Before
    fun setUp() {
        mainViewModel = DemoViewModel(
            getCurrenciesUseCase = getCurrenciesUseCase,
            sortCurrenciesUseCase = sortCurrenciesUseCase
        )
        Dispatchers.setMain(standardTestDispatcher)
    }

    @Test
    fun `provides appropriate Reaction WHEN LoadDataButtonPressed`() = runTest {
        // Given
        val currencies = createUnsortedCurrencyList()
        coEvery { getCurrenciesUseCase() } returns currencies

        // When
        mainViewModel.dispatchEvent(LoadDataButtonPressed)

        // Then
        val expectedReaction = CurrenciesDatasetUpdated(currencies, true)
        assertEquals(expectedReaction, mainViewModel.reaction.value)
    }

    @Test
    fun `provides appropriate sorting WHEN SortCurrenciesButtonPressed once`() = runTest {
        // Given
        val currencies = createCurrencyListSortedAlphabetically()
        coEvery { sortCurrenciesUseCase.sortAlphabetically(any()) } returns currencies

        // When
        mainViewModel.dispatchEvent(SortCurrenciesButtonPressed)

        // Then
        val expectedReaction = CurrenciesDatasetUpdated(currencies, true)
        assertEquals(expectedReaction, mainViewModel.reaction.value)
    }

    @Test
    fun `provides appropriate sorting WHEN SortCurrenciesButtonPressed again`() = runTest {
        // Given
        val currenciesSortedAlphabetically = createCurrencyListSortedAlphabetically()
        val currenciesSortedReverseAlphabetically = createCurrencyListSortedReverseAlphabetically()
        coEvery { sortCurrenciesUseCase.sortAlphabetically(any()) } returns currenciesSortedAlphabetically
        coEvery { sortCurrenciesUseCase.sortReverseAlphabetically(any()) } returns currenciesSortedReverseAlphabetically

        // When
        mainViewModel.dispatchEvent(SortCurrenciesButtonPressed)
        mainViewModel.dispatchEvent(SortCurrenciesButtonPressed)

        // Then
        val expectedReaction = CurrenciesDatasetUpdated(currenciesSortedReverseAlphabetically, true)
        assertEquals(expectedReaction, mainViewModel.reaction.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
