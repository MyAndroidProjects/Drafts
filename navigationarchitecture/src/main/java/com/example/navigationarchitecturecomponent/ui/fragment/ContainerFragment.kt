package com.example.navigationarchitecturecomponent.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.navigationarchitecturecomponent.R
import kotlinx.android.synthetic.main.fragment_container.*

class ContainerFragment : BaseFragment() {
    override fun getViewLayoutId(): Int = R.layout.fragment_container
    lateinit var navFragmentController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        navFragmentController =    Navigation.findNavController(innerFragmentContainer as View)
//        navFragmentController = innerFragmentContainer.findNavController()
//        navFragmentController = innerFragmentNavigation.findNavController()
        navFragmentController = Navigation.findNavController(activity as Activity, innerFragmentNavigation.id)
        setToggleGroupCheckedListener()
    }

    private fun setToggleGroupCheckedListener() {
        containerButtonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    containerButtonSetFirstFragment.id -> {
                        navFragmentController.navigate(R.id.firstSimpleFragment2)
                    }
                    containerButtonSetSecondFragment.id -> {
                        navFragmentController.navigate(R.id.secondSimpleFragment2)
                    }
                    containerButtonSetThirdFragment.id -> {
                        navFragmentController.navigate(R.id.thirdSimpleFragment2)
                    }
                    containerButtonSetFourthFragment.id -> {
                        navFragmentController.navigate(R.id.fourthSimpleFragment2)
                    }
                }
            }
        }
    }
}