package com.prodan.currency.base

import okhttp3.ResponseBody

interface RestStateListener {
    fun onRequestStart()
    fun onRequestFinished()
    fun onRequestHttpError(
        responseBody: ResponseBody?
    )
    fun onRequestNoConnection()
    fun onRequestGeneralError()
}