package com.jbpi.test02

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jbpi.test02.Event.LoadDataButtonPressed
import com.jbpi.test02.Event.SortCurrenciesButtonPressed
import com.jbpi.test02.model.data.CurrencyInfo
import com.jbpi.test02.ui.CurrencyListFragment
import javax.inject.Inject

class DemoActivity : AppCompatActivity() {

    @Inject lateinit var mainViewModelFactory: DemoViewModelFactory

    private lateinit var buttonLoadData: Button
    private lateinit var buttonSortCurrencies: Button
    private lateinit var currencyListContainer: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MyApplication).appComponent.inject(this)
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        val mainViewModel: DemoViewModel by viewModels(factoryProducer = { mainViewModelFactory })

        buttonLoadData = findViewById(R.id.button_load_and_display_data)
        buttonLoadData.setOnClickListener {
            buttonLoadData.isEnabled = false
            mainViewModel.dispatchEvent(LoadDataButtonPressed)
        }

        buttonSortCurrencies = findViewById(R.id.button_sort_currencies)
        buttonSortCurrencies.setOnClickListener {
            buttonSortCurrencies.isEnabled = false
            mainViewModel.dispatchEvent(SortCurrenciesButtonPressed)
        }

        currencyListContainer = findViewById(R.id.currency_list_container)
        currencyListContainer.setContent {
            CurrencyListFragment(emptyList())
        }

        mainViewModel.reaction.observe(this) { reaction ->
            when (reaction) {
                is Reaction.CurrenciesDatasetUpdated -> {
                    updateCurrenciesList(reaction.currencies)
                    buttonLoadData.isEnabled = true
                    buttonSortCurrencies.isEnabled = reaction.sortingEnabled
                }
            }
        }
    }

    private fun updateCurrenciesList(currencies: List<CurrencyInfo>) {
        currencyListContainer.setContent {
            CurrencyListFragment(currencies)
        }
    }
}
