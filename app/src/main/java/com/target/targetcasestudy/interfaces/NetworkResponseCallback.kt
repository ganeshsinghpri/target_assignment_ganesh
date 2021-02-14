package com.target.targetcasestudy.interfaces;

interface NetworkResponseCallback {
    fun onNetworkSuccess()
    fun onNetworkFailure(th : Throwable)
}