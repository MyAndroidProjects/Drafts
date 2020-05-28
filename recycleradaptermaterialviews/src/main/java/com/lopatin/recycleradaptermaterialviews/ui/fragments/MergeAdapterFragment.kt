package com.lopatin.recycleradaptermaterialviews.ui.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.ui.adapters.differentitemtypes.DifferentItemTypesRecyclerAdapter
import com.lopatin.recycleradaptermaterialviews.ui.adapters.moveandswipe.MoveAndSwipeRecyclerAdapter
import com.lopatin.recycleradaptermaterialviews.utils.Utils
import androidx.recyclerview.widget.MergeAdapter
import kotlinx.android.synthetic.main.fragment_merge_adapter.*

class MergeAdapterFragment:BaseFragment() ,
    MoveAndSwipeRecyclerAdapter.MoveAndSwipeAdapterClickListener,
    MoveAndSwipeRecyclerAdapter.DragNDropListener {

    companion object {
        fun getInstance(): MergeAdapterFragment = MergeAdapterFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_merge_adapter
    }

    override fun setRecyclerAdapter() {
        val ctx = context
        ctx ?: return
        val jsonStrFirst = Utils.getJsonStringFromRawJson(ctx, R.raw.employees)
        val listFirst = getListFromJson(jsonStrFirst)
        val jsonStrSecond = Utils.getJsonStringFromRawJson(ctx, R.raw.employees_with_type)
        val listSecond = getListFromJson(jsonStrSecond)
        val firstAdapter = MoveAndSwipeRecyclerAdapter(listFirst, this, this)
        val secondAdapter= DifferentItemTypesRecyclerAdapter(listSecond)
        val mergeAdapter = MergeAdapter(firstAdapter, secondAdapter)
        val layoutManager = LinearLayoutManager(context)

        recyclerViewMergeAdapter.layoutManager = layoutManager
        recyclerViewMergeAdapter.adapter = mergeAdapter
    }

    override fun onItemClick(name: String, surname: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startDragNDrop(holder: MoveAndSwipeRecyclerAdapter.ViewHolder) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}