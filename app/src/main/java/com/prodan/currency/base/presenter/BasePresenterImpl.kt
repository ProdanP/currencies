package com.prodan.currency.base.presenter

import com.prodan.currency.base.view.BaseView
import okhttp3.ResponseBody

open class BasePresenterImpl<V: BaseView> : BasePresenter<V> {
    var view : V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onRequestStart() {
        view?.onShowLoading()
    }

    override fun onRequestFinished() {
        view?.onHideLoading()
    }

    override fun onRequestHttpError(responseBody: ResponseBody?) {
        view?.onShowHttpError(responseBody)
    }

    override fun onRequestNoConnection() {
        view?.onShowConnectivityError()
    }

    override fun onRequestGeneralError() {
        view?.onShowGeneralError()
    }

    override fun onDestroyed() {}
}