package me.ostafin.basicdaggerproject.ui.main

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.kotlin.addTo
import me.ostafin.basicdaggerproject.base.BaseViewModel
import me.ostafin.basicdaggerproject.data.network.ApiService
import me.ostafin.basicdaggerproject.data.network.util.RetrofitException
import me.ostafin.basicdaggerproject.util.asApiAsyncRequest
import me.ostafin.basicdaggerproject.util.livedata.SingleLiveEvent
import me.ostafin.basicdaggerproject.util.subscribeApiAsyncRequest
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val testId: Long,
    private val apiService: ApiService
) : BaseViewModel() {

    private var counter = 0

    private val _showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showToast: LiveData<String> = _showToast

    override fun onInitialize() {
        super.onInitialize()

        helloWorld()
    }

    private fun helloWorld() {
        Timber.d("hello world $testId")

        apiService.getHehe()
            .asApiAsyncRequest()
            .subscribeApiAsyncRequest(
                onProgressChanged = { Timber.d("loading: $it") },
                onSuccess = {
                    Timber.d(it.results.toString())
                },
                onError = {
                    if (it is RetrofitException) {
                        Timber.e(it.getKind().toString())
                    } else {
                        Timber.e("NOT")
                    }
                }
            )
            .addTo(disposables)
    }

    fun buttonClicked() {
        val toastContent = (counter++).toString()
        _showToast.postValue(toastContent)
    }

}