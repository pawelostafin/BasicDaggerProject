package me.ostafin.basicdaggerproject.ui.main

import android.widget.Toast
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*
import me.ostafin.basicdaggerproject.R
import me.ostafin.basicdaggerproject.base.BaseActivity
import kotlin.reflect.KClass

class MainActivity : BaseActivity<MainViewModel>() {

    override val viewModelClass: KClass<MainViewModel>
        get() = MainViewModel::class

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun setupView() {
        super.setupView()

        button.setOnClickListener {
            viewModel.buttonClicked()
        }

    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.showToast.observe(this, ::showToast)
        viewModel.textViewContent.observe(this) { textView.text = it }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}