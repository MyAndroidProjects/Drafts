package com.lopatin.recycleradaptermaterialviews.ui.adapters.movelongclickandswipe

interface MoveLongClickAndSwipeTouchHelperContract {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
}