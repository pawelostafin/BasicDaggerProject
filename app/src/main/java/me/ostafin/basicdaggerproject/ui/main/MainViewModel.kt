package me.ostafin.basicdaggerproject.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.addTo
import me.ostafin.basicdaggerproject.base.BaseViewModel
import me.ostafin.basicdaggerproject.data.network.ApiService
import me.ostafin.basicdaggerproject.data.network.util.RetrofitException
import me.ostafin.basicdaggerproject.domain.repository.EloRepository
import me.ostafin.basicdaggerproject.util.asApiAsyncRequest
import me.ostafin.basicdaggerproject.util.livedata.SingleLiveEvent
import me.ostafin.basicdaggerproject.util.subscribeApiAsyncRequest
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val extraLong: Long,
    private val eloRepository: EloRepository
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

    private val _resultsString: MutableLiveData<String> = MutableLiveData()
    val resultsString: MutableLiveData<String> = _resultsString

    private val booleanRelay: PublishRelay<Boolean> = PublishRelay.create()
    val booleanObs: Observable<Boolean> = booleanRelay

    private val callbackResultRelay: PublishRelay<Long> = PublishRelay.create()
    val callbackResultObs: Observable<Long> = callbackResultRelay

    override fun onInitialize() {
        super.onInitialize()

        helloWorld()
        _textView2Content.postValue(extraLong.toString())
        booleanRelay.accept(true)
        booleanRelay.accept(false)
        booleanRelay.accept(true)

//        emitFalseWithDelay()

        eloRepository.heheCallback {
            callbackResultRelay.accept(it)
        }
    }

    private fun emitFalseWithDelay() {
        Single.just(false)
            .delay(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(booleanRelay)
            .addTo(disposables)
    }

    private fun helloWorld() {
        Timber.d("hello world $extraLong")

        eloRepository.getHeHe(1)
            .asApiAsyncRequest()
            .subscribeApiAsyncRequest(
                onProgressChanged = { Timber.d("loading: $it") },
                onSuccess = {
                    val resultsString = it.results.toString()
                    _resultsString.postValue(resultsString)
                    Timber.d(resultsString)
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