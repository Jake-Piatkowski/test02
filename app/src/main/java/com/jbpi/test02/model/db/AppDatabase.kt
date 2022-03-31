package com.jbpi.test02.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jbpi.test02.model.data.CurrencyInfo

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyInfoDao(): CurrencyInfoDao
}
