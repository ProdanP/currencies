package com.prodan.currency.presentation.currencies

import com.prodan.currency.base.presenter.BasePresenterImpl
import com.prodan.currency.base.view.BaseView
import com.prodan.currency.base.viewholders.Row
import com.prodan.currency.data.models.db.CurrencyLocal
import com.prodan.currency.domain.interactors.InteractorCurrencies
import com.prodan.currency.domain.models.RowCurrencyData
import javax.inject.Inject

class PresenterCurrencies<V : PresenterCurrencies.ICurrenciesView>
@Inject constructor(val interactor: InteractorCurrencies) : BasePresenterImpl<V>(),
    InteractorCurrencies.InteractorCallback {
    var currencyMultiplier: Float? = null

    fun getCurriencies() {
        interactor.getCurrencies(callback = this)
    }

    fun updateReferenceCurrency(referenceCurrencyCode: String, currencyRate: Float) {
        currencyMultiplier = currencyRate
        interactor.updateReferenceCurrency(referenceCurrencyCode)
    }

    override fun onCurrenciesResponse(items: List<CurrencyLocal>) {
        if (items.isNotEmpty()) {
            if (currencyMultiplier == null) {
                currencyMultiplier = items[0].rate
            } else {
                items[0].rate = currencyMultiplier!!
            }
        }

        currencyMultiplier?.let { multiplier ->
            view?.onRowsRetrived(items.map { RowCurrencyData(it, multiplier) })
        }
    }

    fun clearDisposable() {
        interactor.clear()
    }

    fun dispose() {
        interactor.dispose()
    }

    fun applyCurrenciesMultiplier(inputText: String) {
        currencyMultiplier = if (inputText.isNotEmpty())
            inputText.toFloat()
        else
            1f
    }

    interface ICurrenciesView : BaseView {
        fun onRowsRetrived(rows: List<Row>)
    }

}
