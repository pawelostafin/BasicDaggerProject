package me.ostafin.basicdaggerproject.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import me.ostafin.basicdaggerproject.di.viewmodel.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<VM : BaseViewModel> : DaggerFragment() {

    protected abstract val layoutId: Int
    protected abstract val viewModelType: KClass<VM>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val viewModel: VM by lazy {
        ViewModelProvider(this, viewModelFactory).get(viewModelType.java)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(layoutId, container, false)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        observeViewModel()

        viewModel.initializeIfNeeded()
    }

    @CallSuper
    protected open fun setupView() {
    }

    @CallSuper
    protected open fun observeViewModel() {
    }

}