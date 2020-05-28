package com.lopatin.recycleradaptermaterialviews.ui.fragments

import com.lopatin.recycleradaptermaterialviews.R

class DragAndDropFragment : BaseFragment() {
    companion object {
        fun getInstance(): DragAndDropFragment = DragAndDropFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_drag_and_drop
    }

    override fun setRecyclerAdapter() {
     //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}