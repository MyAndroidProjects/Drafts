package com.lopatin.recycleradaptermaterialviews.ui.adapters.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.lopatin.recycleradaptermaterialviews.model.Employee


class DiffUtilCallback(var oldList: ArrayList<Employee>, var newList: ArrayList<Employee>) :
    DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].avatarPath == newList[newItemPosition].avatarPath &&
                oldList[oldItemPosition].firstName == newList[newItemPosition].firstName &&
                oldList[oldItemPosition].surname == newList[newItemPosition].surname &&
                oldList[oldItemPosition].birthday == newList[newItemPosition].birthday &&
                oldList[oldItemPosition].dateOfEmployment == newList[newItemPosition].dateOfEmployment &&
                oldList[oldItemPosition].specialty == newList[newItemPosition].specialty &&
                oldList[oldItemPosition].type == newList[newItemPosition].type &&
                oldList[oldItemPosition].office == newList[newItemPosition].office &&
                oldList[oldItemPosition].skills == newList[newItemPosition].skills
    }
}