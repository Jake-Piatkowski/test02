package com.jbpi.test02.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jbpi.test02.model.data.CurrencyInfo

@Dao
interface CurrencyInfoDao {

    @Query("SELECT * FROM CurrencyInfo")
    fun getAll(): List<CurrencyInfo>

    @Insert
    fun insertAll(currencies: List<CurrencyInfo>)
}
