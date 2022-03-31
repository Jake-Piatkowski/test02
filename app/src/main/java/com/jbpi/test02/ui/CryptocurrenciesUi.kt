package com.jbpi.test02.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jbpi.test02.model.data.CurrencyInfo

@Composable
fun CurrencyListFragment(
    currencies: List<CurrencyInfo>
) {
    when (currencies.isEmpty()) {
        true -> EmptyCurrencyList()
        false -> CurrencyList(currencies = currencies)
    }
}

@Composable
fun CurrencyList(
    currencies: List<CurrencyInfo>
) {
    LazyColumn {
        itemsIndexed(items = currencies) { index, currency ->
            CurrencyItem(currencyInfo = currency)

            if (index < currencies.lastIndex) {
                Divider(thickness = 2.dp)
            }
        }
    }
}

@Composable
fun EmptyCurrencyList() {
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = 24.dp)
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "No currencies to display. Please load currency data.",
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
fun CurrencyItem(
    currencyInfo: CurrencyInfo
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
            .semantics(mergeDescendants = true) {},
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = currencyInfo.name,
            style = MaterialTheme.typography.body1
        )
        Text(
            text = currencyInfo.symbol,
            style = MaterialTheme.typography.subtitle2
        )
    }
}

@Preview
@Composable
fun CurrencyListFragmentExample_non_empty() {
    MaterialTheme {
        CurrencyListFragment(
            currencies = listOf(
                createSampleCurrency(),
                createSampleCurrency(),
                createSampleCurrency()
            )
        )
    }
}

@Preview
@Composable
fun CurrencyListFragmentExample_empty() {
    MaterialTheme {
        CurrencyListFragment(
            currencies = emptyList()
        )
    }
}

@Preview
@Composable
fun CurrencyItemExample() =
    MaterialTheme {
        CurrencyItem(
            currencyInfo = createSampleCurrency()
        )
    }

private fun createSampleCurrency() = CurrencyInfo(
    id = "BTC",
    name = "Bitcoin",
    symbol = "BTC"
)
