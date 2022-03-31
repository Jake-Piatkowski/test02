package com.jbpi.test02.di

import android.app.Application
import androidx.room.Room
import com.jbpi.test02.model.db.AppDatabase
import com.jbpi.test02.model.db.CurrencyInfoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule constructor(
    private val application: Application
) {

    @Singleton
    @Provides
    internal fun provideDatabase(): AppDatabase =
        Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "my-database"
            )
            .build()

    @Provides
    internal fun provideCurrencyInfoDao(database: AppDatabase): CurrencyInfoDao = database.currencyInfoDao()
}
