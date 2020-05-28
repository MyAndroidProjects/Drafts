package com.lopatin.recycleradaptermaterialviews.ui.fragments

import androidx.recyclerview.widget.LinearLayoutManager
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.ui.adapters.differentitemtypes.DifferentItemTypesRecyclerAdapter
import com.lopatin.recycleradaptermaterialviews.utils.Utils
import kotlinx.android.synthetic.main.fragment_different_item_types.*

class DifferentItemTypesFragment : BaseFragment() {
    companion object {
        @Synchronized
        fun getInstance(): DifferentItemTypesFragment = DifferentItemTypesFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_different_item_types
    }


    override fun setRecyclerAdapter() {
        val ctx = context
        ctx ?: return
        val jsonStr = Utils.getJsonStringFromRawJson(ctx, R.raw.employees_with_type)
        val list = getListFromJson(jsonStr)
        val adapter = DifferentItemTypesRecyclerAdapter(list)
        val layoutManager = LinearLayoutManager(context)
        recyclerDifferentTypes.scrollToPosition(5)
        recyclerDifferentTypes.isNestedScrollingEnabled = true
        recyclerDifferentTypes.isVerticalScrollBarEnabled = false
        recyclerDifferentTypes.layoutManager = layoutManager
        recyclerDifferentTypes.adapter = adapter

        // декоратор не ставлю ,т.к. в адаптере элементы в виде карточек
/*        recyclerDifferentTypes.addItemDecoration(
            (MoveAndSwipeAdapterDivider(
                context!!
            ))
        )*/
//        val callback = MoveLongClickAndSwipeAdapterTouchHelperCallback(adapter)
//        val touchHelper = ItemTouchHelper(callback)
//        touchHelper.attachToRecyclerView(recyclerViewMoveLongClickAndSwipe)

    }

}