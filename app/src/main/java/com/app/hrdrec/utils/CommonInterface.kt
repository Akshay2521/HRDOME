package com.app.hrdrec.utils

interface CommonInterface : ProgressLoaderCall {
    fun onErrorMessage(message: String)
    fun onResponseSuccess()
    fun noListData()
}