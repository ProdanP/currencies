package com.prodan.currency.domain.interactors

import com.prodan.currency.base.RestStateListener
import com.prodan.currency.data.models.db.CurrencyLocal
import com.prodan.currency.data.repositories.api.currencies.RepositoryCurrencyRemote
import com.prodan.currency.data.repositories.local_db.currencies.RepositoryCurrencyLocal
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import java.util.concurrent.TimeUnit

open class InteractorCurrencies(
    val repositoryLocal: RepositoryCurrencyLocal,
    val repositoryRemote: RepositoryCurrencyRemote
) {
    private var remoteDisposables = CompositeDisposable()
    private var localDisposables = CompositeDisposable()

    fun getCurrencies(callback: InteractorCallback) {
        localDisposables.clear()
        val disposable =
            repositoryLocal.getLocalCurrenciesData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {callback.onCurrenciesResponse(it)}
        localDisposables.add(disposable)

        syncRates()
    }

    private fun syncRates() {
        val disposable = repositoryLocal.getInitialReferenceCurrency()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { reference ->
                remoteDisposables.clear()

                val remote = Flowable.interval(1, TimeUnit.SECONDS)
                    .flatMapSingle {
                        repositoryRemote.getCurrencies(reference.code)
                    }.flatMapCompletable {
                        repositoryLocal.updateRates(it.rates)
                    }.subscribe()
                remoteDisposables.add(remote)
            }
        localDisposables.add(disposable)
    }

    fun updateReferenceCurrency(referenceCurrencyCode: String) {
        localDisposables.add(
            repositoryLocal.updateReferenceCurrency(referenceCurrencyCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        )
    }

    fun dispose() {
        remoteDisposables.dispose()
        localDisposables.dispose()
    }

    fun clear() {
        remoteDisposables.clear()
        localDisposables.clear()
    }

    interface InteractorCallback : RestStateListener {
        fun onCurrenciesResponse(items: List<CurrencyLocal>)
    }
}