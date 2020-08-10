package me.ostafin.basicdaggerproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val extraLong: Long,
    private val apiService: ApiService
) : BaseViewModel() {

    private var counter = 0

    private val _showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showToast: LiveData<String> = _showToast

    private val _showFragment: SingleLiveEvent<Int> = SingleLiveEvent()
    val showFragment: LiveData<Int> = _showFragment

    private val _textViewContent: MutableLiveData<String> = MutableLiveData()
    val textViewContent: LiveData<String> = _textViewContent

    private val _textView2Content: MutableLiveData<String> = MutableLiveData()
    val textView2Content: LiveData<String> = _textView2Content

    private val _startMainActivity: SingleLiveEvent<Unit> = SingleLiveEvent()
    val startMainActivity: LiveData<Unit> = _startMainActivity

    override fun onInitialize() {
        super.onInitialize()

        helloWorld()
        _textView2Content.postValue(extraLong.toString())
    }

    private fun helloWorld() {
        Timber.d("hello world $extraLong")

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
        val nextNumber = counter++
        val nextNumberString = nextNumber.toString()
        _showToast.postValue(nextNumberString)
        _textViewContent.postValue(nextNumberString)
        _showFragment.postValue(nextNumber)
    }

    fun startActivityButtonClicked() {
        _startMainActivity.call()
    }

}