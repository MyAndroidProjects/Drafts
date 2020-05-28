package com.lopatin.recycleradaptermaterialviews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ArrayListContainer <T>(
    @SerializedName("response")
    @Expose
    val localArrayList: ArrayList<T>
)