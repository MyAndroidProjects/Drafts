package com.example.navigationarchitecturecomponent.model

import java.io.Serializable


data class SimpleData(
    val firstString: String,
    val secondString: String,
    val number: Int
) : Serializable