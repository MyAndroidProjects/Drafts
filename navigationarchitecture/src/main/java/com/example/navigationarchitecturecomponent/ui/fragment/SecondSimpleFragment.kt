package com.example.navigationarchitecturecomponent.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.navigationarchitecturecomponent.R
import com.example.navigationarchitecturecomponent.model.SimpleData
import kotlinx.android.synthetic.main.fragment_second_simple.*

class SecondSimpleFragment : BaseFragment() {

    companion object {
        const val DATA_KEY = "secondDataKey"
    }

    private lateinit var navController: NavController

    override fun getViewLayoutId(): Int = R.layout.fragment_second_simple

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        buttonNextSimpleSecond.setOnClickListener {
            val bundle = Bundle()
            val data = SimpleData("from second", "to first", 21)
            bundle.putSerializable(FirstSimpleFragment.DATA_KEY, data)
            navController.navigate(R.id.action_secondSimpleFragment_to_firstSimpleFragment, bundle)
        }
        val currentData = getDataFromBundle(arguments, DATA_KEY)
        setTextFromData(textSimpleSecond, currentData)
    }
}