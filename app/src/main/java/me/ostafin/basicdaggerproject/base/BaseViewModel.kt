package me.ostafin.basicdaggerproject.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    private var isInitialized: Boolean = false

    fun initializeIfNeeded() {
        if (!isInitialized) {
            isInitialized = true
            onInitialize()
        }
    }

    @CallSuper
    protected open fun onInitialize() {
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }

}