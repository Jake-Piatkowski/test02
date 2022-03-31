package com.jbpi.test02.model

import com.jbpi.test02.utils.createUnsortedCurrencyList
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCurrenciesUseCaseTest {

    private val currencyInfoRepository: CurrencyInfoRepository = mockk(relaxed = true)

    private lateinit var getCurrenciesUseCase: GetCurrenciesUseCase

    @Before
    fun setUp() {
        getCurrenciesUseCase = GetCurrenciesUseCase(currencyInfoRepository)
    }

    @Test
    fun `downloads and persists data WHEN no data in local db`() = runTest {
        // Given
        coEvery { currencyInfoRepository.getCurrenciesData() } returns emptyList()

        // When
        getCurrenciesUseCase()

        // Then
        coVerify { currencyInfoRepository.downloadAndPersistCurrenciesData() }
    }

    @Test
    fun `doesn't download or persist data WHEN data already present in local db`() = runTest {
        // Given
        coEvery { currencyInfoRepository.getCurrenciesData() } returns createUnsortedCurrencyList()

        // When
        getCurrenciesUseCase()

        // Then
        coVerify(exactly = 0) { currencyInfoRepository.downloadAndPersistCurrenciesData() }
    }
}
