package com.prodan.currency.base.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody

abstract class ActivityBase : AppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
    }

    override fun onShowLoading() {

    }

    override fun onHideLoading() {
    }

    override fun onShowHttpError(responseBody: ResponseBody?) {

    }

    override fun onShowConnectivityError() {

    }

    override fun onShowGeneralError() {

    }
}