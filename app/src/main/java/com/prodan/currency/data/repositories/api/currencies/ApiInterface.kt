package com.prodan.currency.data.repositories.api.currencies

import com.prodan.currency.data.models.CurrienciesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{
    @GET("android/latest")
    fun getCurrencies(@Query("base") baseCurrency : String) : Single<CurrienciesResponse>
}