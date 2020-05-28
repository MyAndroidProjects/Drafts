package com.lopatin.recycleradaptermaterialviews.ui.adapters.movelongclickandswipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.model.Employee
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_move_long_click_and_swipe_adapter.view.*
import java.util.*


class MoveLongClickAndSwipeRecyclerAdapter(
    val employeeList: ArrayList<Employee>,
    val clickListener: MoveLongClickAndSwipeAdapterClickListener
) :
    RecyclerView.Adapter<MoveLongClickAndSwipeRecyclerAdapter.ViewHolder>(),
    MoveLongClickAndSwipeTouchHelperContract {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_move_long_click_and_swipe_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employeeList[holder.adapterPosition]
        holder.itemEmployeeView.name.text = employee.firstName
        holder.itemEmployeeView.surname.text = employee.surname
        val avPath: String = if(employee.avatarPath.isNullOrEmpty()){
            "file:///android_asset/avatars/empty_man.jpg"
        }else{
            employee.avatarPath
        }

        Picasso.get()
//            .load(employee.avatarPath)
            .load(avPath)
            .placeholder(R.drawable.empty_man)
            .fit()
            .priority(Picasso.Priority.NORMAL)
            .into(holder.itemEmployeeView.avatarImage)
        holder.itemEmployeeView.setOnClickListener {
            clickListener.onItemClick(employee.firstName, employee.surname)
        }
    }


    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(employeeList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(employeeList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        employeeList.removeAt(position)
        notifyItemRemoved(position)
    }

    interface MoveLongClickAndSwipeAdapterClickListener {
        fun onItemClick(name: String, surname: String)
    }

    class ViewHolder(val itemEmployeeView: View) : RecyclerView.ViewHolder(itemEmployeeView)
}