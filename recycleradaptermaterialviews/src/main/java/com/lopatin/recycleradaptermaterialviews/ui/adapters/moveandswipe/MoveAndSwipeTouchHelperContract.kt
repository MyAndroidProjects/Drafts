package com.lopatin.recycleradaptermaterialviews.ui.adapters.moveandswipe

interface MoveAndSwipeTouchHelperContract {
    fun onRowMoved(fromPosition: Int, toPosition: Int)
    fun onRowSelected(viewHolder: MoveAndSwipeRecyclerAdapter.ViewHolder)
    fun onRowClear(viewHolder: MoveAndSwipeRecyclerAdapter.ViewHolder)
    fun onItemDismiss(position: Int)
}