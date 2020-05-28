package com.lopatin.recycleradaptermaterialviews.ui.adapters.movelongclickandswipe

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MoveLongClickAndSwipeAdapterTouchHelperCallback(private val adapter: MoveLongClickAndSwipeTouchHelperContract) :
    ItemTouchHelper.Callback() {

    /**
     * dragFlags определяют в какие стороны можно переносить item
     * (соответственно ItemTouchHelper.UP or ItemTouchHelper.DOWN - и вверх и вниз)
     *
     * swipeFlags определяют в какие стороны можно свайпать
     * (если сделаем ItemTouchHelper.END or ItemTouchHelper.START то свайпаться будет в обе стороны)
     */
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.END or ItemTouchHelper.START
                return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    /**
     *    return true активирует долгое нажатие, необходимое для переноса item'ов
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    /**
     *    return true активирует swipe
     */
    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

}