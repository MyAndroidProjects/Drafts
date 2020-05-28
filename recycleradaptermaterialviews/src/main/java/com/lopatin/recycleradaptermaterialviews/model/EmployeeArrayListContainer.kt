package com.lopatin.recycleradaptermaterialviews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeArrayListContainer (
    @SerializedName("response")
    @Expose
    val employeeArrayList: ArrayList<Employee>
)