package com.example.navigationarchitecturecomponent.ui.activity

import android.app.TaskStackBuilder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.navigationarchitecturecomponent.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.fragmentNavigation)
        setToggleGroupCheckedListener()
    }

    private fun setToggleGroupCheckedListener() {
        mainButtonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    buttonSetFirstFragment.id -> {
                        navController.navigate(R.id.firstSimpleFragment)
                    }
                    buttonSetSecondFragment.id -> {
                        navController.navigate(R.id.secondSimpleFragment)
                    }
                    buttonSetThirdFragment.id -> {
                        navController.navigate(R.id.thirdSimpleFragment)
                    }
                    buttonSetFourthFragment.id -> {
                        navController.navigate(R.id.fourthSimpleFragment)
                    }
                    buttonSetFragmentContainer.id -> {
                        navController.navigate(R.id.containerFragment)
                    }
                }
            }
        }
    }
}
