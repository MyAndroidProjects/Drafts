package com.lopatin.recycleradaptermaterialviews.ui.fragments

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.ui.adapters.MoveAndSwipeAdapterDivider
import com.lopatin.recycleradaptermaterialviews.ui.adapters.moveandswipe.MoveAndSwipeAdapterTouchHelperCallback
import com.lopatin.recycleradaptermaterialviews.ui.adapters.moveandswipe.MoveAndSwipeRecyclerAdapter
import com.lopatin.recycleradaptermaterialviews.utils.Utils
import kotlinx.android.synthetic.main.fragment_move_and_swipe.*
import org.jetbrains.anko.toast

class MoveAndSwipeFragment : BaseFragment(),
    MoveAndSwipeRecyclerAdapter.MoveAndSwipeAdapterClickListener,
    MoveAndSwipeRecyclerAdapter.DragNDropListener {
    companion object {
        @Synchronized
        fun getInstance(): MoveAndSwipeFragment = MoveAndSwipeFragment()
    }

    var touchHelper: ItemTouchHelper? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_move_and_swipe
    }

    override fun onItemClick(name: String, surname: String) {
        activity?.toast("$name $surname")
    }

    override fun startDragNDrop(holder: MoveAndSwipeRecyclerAdapter.ViewHolder) {
        if (touchHelper != null) {
            Log.d("logDrap", "touchHelper != null")
            touchHelper!!.startDrag(holder)
        } else {
            Log.d("logDrap", "touchHelper == null")
        }
    }

    override fun setRecyclerAdapter() {
        val ctx = context
        ctx ?: return
        val jsonStr = Utils.getJsonStringFromRawJson(ctx, R.raw.employees)
        val list = getListFromJson(jsonStr)
        val adapter = MoveAndSwipeRecyclerAdapter(list, this, this)
        val layoutManager = LinearLayoutManager(context)

        recyclerViewMoveAndSwipe.layoutManager = layoutManager
        recyclerViewMoveAndSwipe.adapter = adapter
        recyclerViewMoveAndSwipe.addItemDecoration(
            (MoveAndSwipeAdapterDivider(
                context!!
            ))
        )
        val callback = MoveAndSwipeAdapterTouchHelperCallback(adapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper?.attachToRecyclerView(recyclerViewMoveAndSwipe)

    }

}