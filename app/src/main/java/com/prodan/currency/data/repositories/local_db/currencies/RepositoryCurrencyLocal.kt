package com.prodan.currency.data.repositories.local_db.currencies

import com.prodan.currency.data.models.db.CurrencyLocal
import io.reactivex.Completable

class RepositoryCurrencyLocal(val dao: CurrenciesDao) {
    fun getLocalCurrenciesData() = dao.getLocalCurrenciesData()

    fun getInitialReferenceCurrency() = dao.getReferenceCurrency()

    fun updateRates(rates : HashMap<String, Float>) = Completable.fromAction {
        dao.updateRates(rates)
    }

    fun updateReferenceCurrency(referenceCurrencyCode: String) = Completable.fromAction {
        dao.updateReferenceCurrency(referenceCurrencyCode)
    }
}