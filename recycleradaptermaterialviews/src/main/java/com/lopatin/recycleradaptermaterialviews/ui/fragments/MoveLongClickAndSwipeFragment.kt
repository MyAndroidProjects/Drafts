package com.lopatin.recycleradaptermaterialviews.ui.fragments

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.ui.adapters.MoveAndSwipeAdapterDivider
import com.lopatin.recycleradaptermaterialviews.ui.adapters.movelongclickandswipe.MoveLongClickAndSwipeAdapterTouchHelperCallback
import com.lopatin.recycleradaptermaterialviews.ui.adapters.movelongclickandswipe.MoveLongClickAndSwipeRecyclerAdapter
import com.lopatin.recycleradaptermaterialviews.utils.Utils
import kotlinx.android.synthetic.main.fragment_move_long_click_and_swipe.*
import org.jetbrains.anko.toast


class MoveLongClickAndSwipeFragment : BaseFragment(),
    MoveLongClickAndSwipeRecyclerAdapter.MoveLongClickAndSwipeAdapterClickListener {

    companion object {
        @Synchronized
        fun getInstance(): MoveLongClickAndSwipeFragment = MoveLongClickAndSwipeFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_move_long_click_and_swipe
    }

    /**
     * если ставить адаптер в onStart или в onResume, то при сворачивании приложения
     * и его последующем разворачивании эти методы стабатывают и адаптер ведет себя некорректно:
     * добавляется дополнительный offset из diviver и такде не отрабатывается  ItemTouchHelper.Callback()
     */
    override fun onStart() {
        super.onStart()
//        setRecyclerAdapter()
        Log.d("logLife", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("logLife", "onResume")
//        setRecyclerAdapter()
    }

    override fun setRecyclerAdapter() {
        val ctx = context
        ctx ?: return
        val jsonStr = Utils.getJsonStringFromRawJson(ctx, R.raw.employees)
        val list = getListFromJson(jsonStr)
        val adapter = MoveLongClickAndSwipeRecyclerAdapter(list, this)
        val layoutManager = LinearLayoutManager(context)

        recyclerViewMoveLongClickAndSwipe.layoutManager = layoutManager
        recyclerViewMoveLongClickAndSwipe.adapter = adapter
        recyclerViewMoveLongClickAndSwipe.addItemDecoration(
            (MoveAndSwipeAdapterDivider(
                context!!
            ))
        )
        val callback = MoveLongClickAndSwipeAdapterTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(recyclerViewMoveLongClickAndSwipe)

    }


    override fun onItemClick(name: String, surname: String) {
        activity?.toast("$name $surname")
    }

}