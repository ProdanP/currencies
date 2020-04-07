package com.prodan.currency.presentation.currencies

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prodan.currency.R
import com.prodan.currency.base.view.ActivityBase
import com.prodan.currency.base.viewholders.Row
import com.prodan.currency.domain.models.RowCurrencyData
import com.prodan.currency.presentation.currencies.adapter.AdapterCurrencies
import com.prodan.currency.presentation.currencies.adapter.ViewHolderCurrency
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.android.synthetic.main.activity_curriencies.*
import javax.inject.Inject


class ActivityCurriencies : ActivityBase(), PresenterCurrencies.ICurrenciesView,
    HasActivityInjector {
    @Inject
    lateinit var presenter: PresenterCurrencies<PresenterCurrencies.ICurrenciesView>

    private val adapter = AdapterCurrencies()

    override fun getLayoutResourceId(): Int = R.layout.activity_curriencies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onAttach(this)
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        presenter.getCurriencies()
    }

    override fun onStop() {
        super.onStop()
        presenter.clearDisposable()
    }

    private fun initRecyclerView() {
        adapter.listener = object : ViewHolderCurrency.ICurrencyActions {
            override fun onItemClick(position: Int) {
                swapRows(position)
            }

            override fun onItemTextChanged(inputText: String) {
                presenter.applyCurrenciesMultiplier(inputText)
            }
        }
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                recyclerView.scrollToPosition(0)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onRowsRetrived(rows: List<Row>) {
        adapter.notifyChanges(rows)
    }

    private fun swapRows(position: Int) {
        val item = adapter.rows[position] as RowCurrencyData
        presenter.updateReferenceCurrency(item.currency.code, currencyRate = item.currency.rate)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }

    @Inject
    lateinit var activityAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityAndroidInjector
}