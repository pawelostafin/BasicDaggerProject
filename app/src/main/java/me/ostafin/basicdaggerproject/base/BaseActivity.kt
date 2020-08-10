package me.ostafin.basicdaggerproject.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import me.ostafin.basicdaggerproject.di.viewmodel.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<VM : BaseViewModel> : DaggerAppCompatActivity() {

    protected abstract val viewModelClass: KClass<VM>
    protected abstract val layoutResId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    protected val viewModel: VM by lazy {
        ViewModelProvider(this, viewModelFactory).get(viewModelClass.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        setupView()
        observeViewModel()

        viewModel.initializeIfNeeded()
    }

    @CallSuper
    open fun setupView() {
    }

    @CallSuper
    open fun observeViewModel() {
    }

}