package me.ostafin.basicdaggerproject.ui.main

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*
import me.ostafin.basicdaggerproject.R
import me.ostafin.basicdaggerproject.base.BaseActivity
import me.ostafin.basicdaggerproject.ui.main.fragmnet.FirstFragment
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

        button2.setOnClickListener {
            viewModel.startActivityButtonClicked()
        }

    }

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.showToast.observe(this, ::showToast)
        viewModel.textViewContent.observe(this) { textView.text = it }
        viewModel.showFragment.observe(this) { showFragment(it) }
        viewModel.startMainActivity.observe(this) { MainActivity.start(this, 909) }
        viewModel.textView2Content.observe(this) { textView2.text = it }
    }

    private fun showFragment(extraInt: Int) {
        supportFragmentManager.beginTransaction().apply {
            val fragment = FirstFragment.newInstance(extraInt)
            replace(R.id.fragmentFrame, fragment)
            addToBackStack("name")
            commitAllowingStateLoss()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    companion object {

        const val EXTRA_LONG = "EXTRA_LONG"

        fun start(context: Context, extraLong: Long) {
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra(EXTRA_LONG, extraLong)
            }
            context.startActivity(intent)
        }
    }

}