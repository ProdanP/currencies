package com.prodan.currency.di.modules

import com.prodan.currency.data.repositories.api.currencies.RepositoryCurrencyRemote
import com.prodan.currency.data.repositories.local_db.currencies.RepositoryCurrencyLocal
import com.prodan.currency.domain.interactors.InteractorCurrencies
import dagger.Module
import dagger.Provides

@Module
class InteractorsModule {
    @Provides
    fun providesInteractorCurrencies(
        repositoryLocal: RepositoryCurrencyLocal,
        repositoryRemote: RepositoryCurrencyRemote
    ) =
        InteractorCurrencies(repositoryLocal, repositoryRemote)
}