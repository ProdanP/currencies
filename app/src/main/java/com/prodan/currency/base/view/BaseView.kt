package com.prodan.currency.base.view

import androidx.annotation.LayoutRes
import okhttp3.ResponseBody

interface BaseView {
    @LayoutRes
    fun getLayoutResourceId(): Int

    fun onShowLoading()
    fun onHideLoading()

    fun onShowHttpError(
        responseBody: ResponseBody?
    )

    fun onShowConnectivityError()
    fun onShowGeneralError()
}