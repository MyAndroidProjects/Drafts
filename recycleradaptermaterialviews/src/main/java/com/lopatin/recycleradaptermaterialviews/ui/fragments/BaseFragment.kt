package com.lopatin.recycleradaptermaterialviews.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.lopatin.recycleradaptermaterialviews.model.Employee
import com.lopatin.recycleradaptermaterialviews.model.EmployeeArrayListContainer

abstract class BaseFragment:Fragment() {

    abstract fun getLayoutId():Int
    abstract fun setRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerAdapter()
    }

    fun getListFromJson(jsonString: String): ArrayList<Employee> {
        val listFromJson: EmployeeArrayListContainer =
            Gson().fromJson(jsonString, EmployeeArrayListContainer::class.java)
        return listFromJson.employeeArrayList
    }
}