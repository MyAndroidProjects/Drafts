package com.lopatin.recycleradaptermaterialviews.ui.adapters.moveandswipe

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MoveAndSwipeAdapterTouchHelperCallback(val adapter: MoveAndSwipeTouchHelperContract) :
    ItemTouchHelper.Callback() {


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START
        return makeMovementFlags(dragFlags, swipeFlags)
        // чтобы не отлавливать swipe
//        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, 0)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is MoveAndSwipeRecyclerAdapter.ViewHolder) {
            adapter.onRowClear(viewHolder)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder is MoveAndSwipeRecyclerAdapter.ViewHolder) {
            adapter.onRowSelected(viewHolder)
        }
        super.onSelectedChanged(viewHolder, actionState)

    }

    /**
     * если return true, то при долгом нажатии в любом месте на item можно можно его перетаскивать
     * также в данной реализации сохраняется перетаскивание по иконке меню
     *
     * если return false, то долгий клик обрабатываться не будет (будет работать только перетаскивание по иконке)
     */
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }


}