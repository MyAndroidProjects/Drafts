package com.lopatin.recycleradaptermaterialviews.ui.adapters.moveandswipe

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.model.Employee
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_move_and_swipe_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class MoveAndSwipeRecyclerAdapter(
    private val employeeList: ArrayList<Employee>,
    private val clickListener: MoveAndSwipeAdapterClickListener,
    private val dragNDropListener: DragNDropListener
) :
    RecyclerView.Adapter<MoveAndSwipeRecyclerAdapter.ViewHolder>(),
    MoveAndSwipeTouchHelperContract {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_move_and_swipe_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val employee = employeeList[holder.adapterPosition]
        holder.itemEmployeeView.name.text = employee.firstName
        holder.itemEmployeeView.surname.text = employee.surname
        val avPath: String = if (employee.avatarPath.isNullOrEmpty()) {
            "file:///android_asset/avatars/empty_man.jpg"
        } else {
            employee.avatarPath
        }

        Picasso.get()
//            .load(employee.avatarPath)
            .load(avPath)
            .placeholder(R.drawable.empty_man)
            .fit()
            .priority(Picasso.Priority.NORMAL)
            .into(holder.itemEmployeeView.avatarImage)
        holder.itemEmployeeView.avatarImage.setOnClickListener {
            clickListener.onItemClick(employee.firstName, employee.surname)
        }
        holder.itemEmployeeView.textLinearLayout.setOnClickListener {
            clickListener.onItemClick(employee.firstName, employee.surname)
        }

        /**
         * отлавливает событие и принудительно запускаем старт метода startDrag класса
         * public class ItemTouchHelper extends RecyclerView.ItemDecoration
         * implements RecyclerView.OnChildAttachStateChangeListener
         * в конце цепочки
         */

        holder.itemEmployeeView.menuImage.setOnTouchListener { v, event ->
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dragNDropListener.startDragNDrop(holder)
                Log.d("logDrap", " menuImage OnTouchListener ")
            }
            return@setOnTouchListener false
        }

    }

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
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
    }

    /**
     * при выборе элемента можно, например, считать количество перемещений, или менять цвет фона элемента
     */
    override fun onRowSelected(viewHolder: ViewHolder) {
        viewHolder.itemEmployeeView.rootConstraintLayout.setBackgroundColor(
            ContextCompat.getColor(
                viewHolder.itemEmployeeView.context,
                R.color.green_300
            )
        )
    }

    /**
     * при "отпускании" элемента можно, например, менять цвет фона элемента на прежний
     */
    override fun onRowClear(viewHolder: ViewHolder) {
        viewHolder.itemEmployeeView.rootConstraintLayout.setBackgroundColor(
            ContextCompat.getColor(
                viewHolder.itemEmployeeView.context,
                R.color.white
            )
        )
    }

    override fun onItemDismiss(position: Int) {
        employeeList.removeAt(position)
        notifyItemRemoved(position)
    }

    interface MoveAndSwipeAdapterClickListener {
        fun onItemClick(name: String, surname: String)
    }

    // запускает процесс переноса
    interface DragNDropListener {
        fun startDragNDrop(holder: ViewHolder)
    }

    class ViewHolder(val itemEmployeeView: View) : RecyclerView.ViewHolder(itemEmployeeView)
}