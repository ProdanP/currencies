package com.prodan.currency.data.repositories.local_db.currencies

import androidx.room.*
import com.prodan.currency.data.models.db.CurrencyLocal
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocalCurriencies(curriencies: List<CurrencyLocal>)

    @Query("SELECT * from currency_local order by isReference = 1 DESC")
    fun getLocalCurrenciesData(): Flowable<List<CurrencyLocal>>

    @Query("SELECT * from currency_local where isReference = 1 LIMIT 1")
    fun getReferenceCurrency(): Flowable<CurrencyLocal>

    @Query("UPDATE currency_local set rate = :rate where code = :code")
    fun updateRate(code: String, rate: Float)

    @Query("UPDATE currency_local set isReference = 1 where code = :referenceCurrencyCode")
    fun markAsReferenceCurrency(referenceCurrencyCode: String)

    @Query("UPDATE currency_local set isReference = 0 where (code is not :referenceCurrencyCode)")
    fun unmarkAsReferenceCurrency(referenceCurrencyCode: String)

    @Transaction
    fun updateRates(rates: HashMap<String, Float>) {
        rates.keys.forEach {
            updateRate(it, rates[it] ?: 0f)
        }
       /* markAsReferenceCurrency(referenceCurrencyCode)
        unmarkAsReferenceCurrency(referenceCurrencyCode)*/
    }

    @Transaction
    fun updateReferenceCurrency(referenceCurrencyCode: String){
        markAsReferenceCurrency(referenceCurrencyCode)
        unmarkAsReferenceCurrency(referenceCurrencyCode)
    }

}