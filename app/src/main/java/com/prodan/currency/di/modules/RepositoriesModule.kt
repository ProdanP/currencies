package com.prodan.currency.di.modules

import android.content.Context
import com.prodan.currency.data.repositories.api.currencies.ApiInterface
import com.prodan.currency.data.repositories.api.currencies.RepositoryCurrencyRemote
import com.prodan.currency.data.repositories.local_db.ApplicationDatabase
import com.prodan.currency.data.repositories.local_db.currencies.CurrenciesDao
import com.prodan.currency.data.repositories.local_db.currencies.RepositoryCurrencyLocal
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoriesModule{
    @Provides
    @Singleton
    fun providesCurrencyApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesCurrencyRepository(apiInterface: ApiInterface): RepositoryCurrencyRemote {
        return RepositoryCurrencyRemote(apiInterface)
    }

    @Provides
    @Singleton
    fun providesCurriencesDao(context : Context) = ApplicationDatabase.getInstance(context).currienciesDao()

    @Provides
    @Singleton
    fun provicesRepositoryCurrenciesLocal(dao : CurrenciesDao) = RepositoryCurrencyLocal(dao)
}