package com.example.navigationarchitecturecomponent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.navigationarchitecturecomponent.R
import com.example.navigationarchitecturecomponent.model.SimpleData
import kotlinx.android.synthetic.main.fragment_chain.*

class FragmentChain : BaseFragment() {

    companion object {
        const val DATA_KEY = "chainDataKey"
    }

    private lateinit var navController: NavController

    override fun getViewLayoutId(): Int = R.layout.fragment_chain

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        buttonNextChain.setOnClickListener {
            val bundle = Bundle()
            val data = SimpleData("from chain", "to second", 552)
            bundle.putSerializable(SecondSimpleFragment.DATA_KEY, data)
            navController.navigate(R.id.action_fragmentChain_to_secondSimpleFragment, bundle)
        }
        buttonBackChain.setOnClickListener {
            navController.popBackStack()
        }
        val currentData = getDataFromBundle(arguments, DATA_KEY)
        setTextFromData(textChain, currentData)
    }
}