package com.example.navigationarchitecturecomponent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.navigationarchitecturecomponent.R
import com.example.navigationarchitecturecomponent.model.SimpleData
import kotlinx.android.synthetic.main.fragment_first_simple.*

class FirstSimpleFragment : BaseFragment() {

    companion object {
        const val DATA_KEY = "firstDataKey"
    }

    private lateinit var navController: NavController

    override fun getViewLayoutId(): Int = R.layout.fragment_first_simple

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        buttonNextSimpleFirst.setOnClickListener {
            val bundle = Bundle()
            val data = SimpleData("from first", "to chain", 155)
            bundle.putSerializable(FragmentChain.DATA_KEY, data)
            navController.navigate(R.id.action_firstSimpleFragment_to_fragmentChain, bundle)
        }
        val currentData = getDataFromBundle(arguments, DATA_KEY)
        setTextFromData(textSimpleFirst, currentData)
    }
}