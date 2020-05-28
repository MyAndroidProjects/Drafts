package com.lopatin.recycleradaptermaterialviews.model

data class Employee(
    val id: Int,
    val firstName: String,
    val surname: String,
    val birthday: String,
    val avatarPath: String,
    val specialty: String,
    val dateOfEmployment: String,
    val office: String,
    val type: Int,
    val skills: ArrayList<String>
)
