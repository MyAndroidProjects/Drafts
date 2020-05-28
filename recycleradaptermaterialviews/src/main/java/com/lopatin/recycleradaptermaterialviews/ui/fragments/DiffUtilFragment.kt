package com.lopatin.recycleradaptermaterialviews.ui.fragments

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.ui.adapters.diffutil.DiffUtilCallback
import com.lopatin.recycleradaptermaterialviews.ui.adapters.diffutil.DiffUtilRecyclerAdapter
import com.lopatin.recycleradaptermaterialviews.utils.Utils
import kotlinx.android.synthetic.main.fragment_diff_util.*

/**
 * Для работы с DiffUtil необходимо реализовать DiffUtil.Callback,
 * вызвать метод calculateDiff(@NonNull Callback cb) и применить к RecyclerView полученный DiffResult методом dispatchUpdatesTo().
 */
class DiffUtilFragment : BaseFragment() {
    companion object {
        fun getInstance(): DiffUtilFragment = DiffUtilFragment()
    }

    var adapter: DiffUtilRecyclerAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_diff_util
    }

    override fun setRecyclerAdapter() {
        val ctx = context
        ctx ?: return
        val jsonStr = Utils.getJsonStringFromRawJson(ctx, R.raw.employees_with_type)
        val list = getListFromJson(jsonStr)
        adapter = DiffUtilRecyclerAdapter(list)
        val layoutManager = LinearLayoutManager(context)

        recyclerViewDiffUtil.layoutManager = layoutManager
        recyclerViewDiffUtil.adapter = adapter

/*        val callback = MoveAndSwipeAdapterTouchHelperCallback(adapter)
        touchHelper = ItemTouchHelper(callback)
        touchHelper?.attachToRecyclerView(recyclerViewMoveAndSwipe)*/

    }

    private fun changeList() {
        val oldList = adapter?.getAdapterEmployeeList()
        oldList ?: return
        val ctx = context
        ctx ?: return
        val jsonStr = Utils.getJsonStringFromRawJson(ctx, R.raw.employees_with_type_mixed)
        val newList = getListFromJson(jsonStr)
        // создаем DiffUtilCallback и передаем в него старый и новый листы
        val diffUtilCallback = DiffUtilCallback(oldList, newList)
        // методом calculateDiff получаем результат
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)

        adapter?.setEmployeeListToAdapter(newList)
        // методом dispatchUpdatesTo применяем изменения из diffResult к нашему адаптеру
        diffResult.dispatchUpdatesTo(adapter as RecyclerView.Adapter<DiffUtilRecyclerAdapter.ViewHolder>)
    }

    override fun onStart() {
        super.onStart()
/*        rootLinearLayout.getLayoutTransition().disableTransitionType(
            LayoutTransition.DISAPPEARING
        )*/
//        rootLinearLayout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING)
/*
        setAllViews()
        val handler = Handler()
        handler.postDelayed({
            expandAllViews()
        }, 2000)
        handler.postDelayed({
            minimizeAllViews()
        }, 4000)*/
        val handler = Handler()
        handler.postDelayed({
            changeList()
        }, 4000)
    }

    fun setAllViews() {
        val ctx = context
        ctx ?: return
        val view = LayoutInflater.from(context).inflate(R.layout.item_expand_card, null, false)

        rootLinearLayout.addView(view)
        val params = view.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, Utils.dpToPx(ctx, 10f).toInt(), 0, 0)
        val view2 = LayoutInflater.from(context).inflate(R.layout.item_expand_card, null, false)

        rootLinearLayout.addView(view2)
        val params2 = view2.layoutParams as ViewGroup.MarginLayoutParams
        params2.setMargins(0, Utils.dpToPx(ctx, -100f).toInt(), 0, 0)
        val view3 = LayoutInflater.from(context).inflate(R.layout.item_expand_card, null, false)
        rootLinearLayout.addView(view3)
        val params3 = view3.layoutParams as ViewGroup.MarginLayoutParams
        params3.setMargins(0, Utils.dpToPx(ctx, -110f).toInt(), 0, 0)
    }

    fun minimizeAllViews() {
        val ctx = context
        ctx ?: return
        val margins = arrayOf(
            Utils.dpToPx(ctx, 10f).toInt(),
            Utils.dpToPx(ctx, -100f).toInt(),
            Utils.dpToPx(ctx, -110f).toInt()
        )
        val ch = rootLinearLayout.children.toList()
        var count = 0
        Log.d("logCard", "minimizeAllViews")
        for (child in ch) {
            val params = child.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, margins[count], 0, 0)
            child.layoutParams = params
            count++
        }
    }

    fun expandAllViews() {
        val ctx = context
        ctx ?: return
        Log.d("logCard", "expandAllViews")

        val ch = rootLinearLayout.children.toList()

        for (child in ch) {
            val params = child.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 0, 0, 0)

            child.layoutParams = params

        }
    }
}