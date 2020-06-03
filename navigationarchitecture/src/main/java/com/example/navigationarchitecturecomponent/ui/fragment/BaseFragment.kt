package com.example.navigationarchitecturecomponent.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navigationarchitecturecomponent.model.SimpleData
import com.google.android.material.textview.MaterialTextView

abstract class BaseFragment : Fragment() {

    abstract fun getViewLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getViewLayoutId(), container, false)
    }

    fun getDataFromBundle(args: Bundle?, key: String): SimpleData? {
        args ?: return null
        val serializableData = args.getSerializable(key)
        return if (serializableData is SimpleData) {
            serializableData
        } else {
            null
        }
    }

    fun setTextFromData(textView: MaterialTextView, data: SimpleData?) {
        data ?: return
        textView.text = data.toString()
    }
}