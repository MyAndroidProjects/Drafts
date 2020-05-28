package com.example.navigationarchitecturecomponent.ui.fragment

import android.os.Bundle
import android.view.View
import com.example.navigationarchitecturecomponent.R
import kotlinx.android.synthetic.main.fragment_simple.*

abstract class BaseSimpleFragment : BaseFragment() {
    override fun getViewLayoutId(): Int = R.layout.fragment_simple

    abstract fun getMainTextContent(): String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMainText(getMainTextContent())
    }

    private fun setMainText(text: String) {
        simpleText.text = text
    }
}