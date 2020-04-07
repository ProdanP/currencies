package com.prodan.currency.data.repositories.api.currencies

import com.prodan.currency.data.models.CurrienciesResponse
import io.reactivex.Single
import javax.inject.Inject

class RepositoryCurrencyRemote @Inject constructor(val apiInterface: ApiInterface) {
    fun getCurrencies(baseCurrency : String) : Single<CurrienciesResponse> = apiInterface.getCurrencies(baseCurrency)
}
