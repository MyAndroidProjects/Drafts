package com.lopatin.recycleradaptermaterialviews.ui.adapters.differentitemtypes

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lopatin.recycleradaptermaterialviews.R
import kotlinx.android.synthetic.main.item_different_item_type_list_view.view.*

class DifferentItemTypeListViewRecyclerAdapter(
    val skillList: ArrayList<String>,
    val skillClickListener: SkillClickListener,
    val employeeId: Int,
    val parentAdapterPosition: Int
) :
    RecyclerView.Adapter<DifferentItemTypeListViewRecyclerAdapter.TypeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_different_item_type_list_view, parent, false)
        return TypeListViewHolder(view)

    }

    override fun getItemCount(): Int {
        return skillList.size
    }

    override fun onBindViewHolder(holder: TypeListViewHolder, position: Int) {
        val skill = skillList[holder.adapterPosition]
        holder.view.itemListSkillText.text = skill
        holder.view.itemListAddSkillToFavoritesButton.setOnClickListener {
            skillClickListener.addSkill(skill, employeeId, parentAdapterPosition)
            Log.d("logListView", "skill $skill")
        }
    }

    interface SkillClickListener {
        fun addSkill(skill: String, employeeId: Int, parentAdapterPosition: Int)
    }

    class TypeListViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}