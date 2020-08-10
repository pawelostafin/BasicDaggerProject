package me.ostafin.basicdaggerproject.ui.main.fragmnet

import android.os.Bundle
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.fragment_first.*
import me.ostafin.basicdaggerproject.R
import me.ostafin.basicdaggerproject.base.BaseFragment
import kotlin.reflect.KClass

class FirstFragment : BaseFragment<FirstViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_first

    override val viewModelType: KClass<FirstViewModel>
        get() = FirstViewModel::class

    override fun observeViewModel() {
        super.observeViewModel()

        viewModel.textViewContent.observe(this) { textView.text = it }
    }

    companion object {

        const val EXTRA_FRAGMENT_INT = "EXTRA_FRAGMENT_INT"

        fun newInstance(extraInt: Int): FirstFragment {
            return FirstFragment().apply {
                arguments = Bundle().apply {
                    putInt(EXTRA_FRAGMENT_INT, extraInt)
                }
            }
        }
    }

}