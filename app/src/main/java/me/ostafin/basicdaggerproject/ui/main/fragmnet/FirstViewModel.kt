package me.ostafin.basicdaggerproject.ui.main.fragmnet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import me.ostafin.basicdaggerproject.base.BaseViewModel
import javax.inject.Inject

class FirstViewModel @Inject constructor(
    private val intValue: Int
) : BaseViewModel() {

    private val _textViewContent: MutableLiveData<String> = MutableLiveData()
    val textViewContent: LiveData<String> = _textViewContent

    override fun onInitialize() {
        super.onInitialize()

        _textViewContent.postValue(intValue.toString())
    }

}