package com.lopatin.recycleradaptermaterialviews.ui.adapters.diffutil

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.model.Employee
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_diff_util_adapter.view.*

class DiffUtilRecyclerAdapter(var employeeList: ArrayList<Employee>) :
    RecyclerView.Adapter<DiffUtilRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diff_util_adapter, parent, false)
        return ViewHolder(view)
    }

    fun setEmployeeListToAdapter(employeeList: ArrayList<Employee>) {
        this.employeeList = employeeList
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    fun getAdapterEmployeeList(): ArrayList<Employee> {
        return employeeList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = employeeList[holder.adapterPosition]
        holder.view.diffUtilsName.text = item.firstName
        holder.view.diffUtilsSurname.text = item.surname
        holder.view.diffUtilsBirthday.text = item.birthday
        holder.view.diffUtilsSpecialty.text = item.specialty
        holder.view.diffUtilsDateOfEmployment.text = item.dateOfEmployment
        holder.view.diffUtilsOffice.text = item.office
        val avPath: String = if (item.avatarPath.isNullOrEmpty()) {
            "file:///android_asset/avatars/empty_man.jpg"
        } else {
            item.avatarPath
        }

        Picasso.get()
//            .load(employee.avatarPath)
            .load(avPath)
            .placeholder(R.drawable.empty_man)
            .fit()
            .priority(Picasso.Priority.NORMAL)
            .into(holder.view.diffUtilsAvatarImage)

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}