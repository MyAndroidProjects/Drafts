package com.example.navigationarchitecturecomponent.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.navigationarchitecturecomponent.R
import com.example.navigationarchitecturecomponent.model.SimpleData
import com.example.navigationarchitecturecomponent.ui.fragment.FirstSimpleFragment

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.fragmentNavigation)
        val bundle = Bundle()
        val data = SimpleData("Start", "Start", 0)
        bundle.putSerializable(FirstSimpleFragment.DATA_KEY, data)
        navController.navigate(R.id.firstSimpleFragment, bundle)
    }

}
