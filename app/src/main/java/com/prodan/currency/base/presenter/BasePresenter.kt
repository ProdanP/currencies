package com.prodan.currency.base.presenter

import com.prodan.currency.base.view.BaseView
import com.prodan.currency.base.RestStateListener

interface BasePresenter<V : BaseView> :
    RestStateListener {
    fun onDestroyed()

    fun onAttach(view: V)
}
