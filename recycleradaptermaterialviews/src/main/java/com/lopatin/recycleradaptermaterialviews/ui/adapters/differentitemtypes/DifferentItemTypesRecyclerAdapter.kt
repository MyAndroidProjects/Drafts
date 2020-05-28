package com.lopatin.recycleradaptermaterialviews.ui.adapters.differentitemtypes

import android.content.Context
import android.text.Editable
import android.text.SpannableStringBuilder
import android.util.Log
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.set
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputLayout
import com.lopatin.recycleradaptermaterialviews.R
import com.lopatin.recycleradaptermaterialviews.model.Employee
import com.lopatin.recycleradaptermaterialviews.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_different_item_types_button.view.*
import kotlinx.android.synthetic.main.item_different_item_types_image.view.*
import kotlinx.android.synthetic.main.item_different_item_types_info.view.*
import kotlinx.android.synthetic.main.item_different_item_types_list.view.*
import kotlinx.android.synthetic.main.item_different_item_types_short.view.*

class DifferentItemTypesRecyclerAdapter(private val employeeList: ArrayList<Employee>) :
    RecyclerView.Adapter<DifferentItemTypesRecyclerAdapter.BaseViewHolder>(),
    DifferentItemTypeListViewRecyclerAdapter.SkillClickListener {
    /**
     *   HashMap<Integer, Object> это SparseArray<Object>
     *   HashMap<Integer, Boolean> это SparseBooleanArray
     *   HashMap<Integer, Integer> это SparseIntArray
     *   HashMap<Integer, Long> это SparseLongArray
     *   HashMap<Long, Object> это LongSparseArray<Object>
     */
    private val favoritesSkills = SparseArray<ArrayList<String>>()
    private val isOpenNestedRecyclerView = SparseBooleanArray()
    var countBind =0
    var countCreate =0
    init {
        Log.d("logAdapter", "employeeList ${employeeList.size}")
        for (employee in employeeList) {
            favoritesSkills.put(employee.id, ArrayList<String>())
            isOpenNestedRecyclerView.put(employee.id, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        countCreate++
        Log.d("logAdapter", "onCreateViewHolder $countCreate")
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (ItemType.getItemTypeByOrdinal(viewType)) {
            ItemType.INFO -> {
                val view = layoutInflater.inflate(
                    R.layout.item_different_item_types_info,
                    parent,
                    false
                )
                InfoViewHolder(view)
            }
            ItemType.BUTTON -> {
                val view = layoutInflater.inflate(
                    R.layout.item_different_item_types_button,
                    parent,
                    false
                )
                ButtonViewHolder(view)
            }
            ItemType.IMAGE -> {
                val view = layoutInflater.inflate(
                    R.layout.item_different_item_types_image,
                    parent,
                    false
                )
                ImageViewHolder(view)
            }
            ItemType.LIST -> {
                val view = layoutInflater.inflate(
                    R.layout.item_different_item_types_list,
                    parent,
                    false
                )
                ListViewHolder(view)
            }
            else -> {
                val view =
                    layoutInflater.inflate(R.layout.item_different_item_types_short, parent, false)
                ShortViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    /**
     * если пользоваться kotlinx.android.synthetic,
     * то у разных layout'ов при одинаковых id у вьюх будет ссылка на первый имплементированный layout
     * например shortView.name и infoView.name будут ссылаться на item_different_item_types_short.xml,
     * а на на item_different_item_types_short.xml и item_different_item_types_info.xml,
     * если импортировать сначала kotlinx.android.synthetic, а потом добавлять вьюхи с разных layout'ов
     * то будет ошибка Overload resolution ambiguity
     * поэтому надо либо переименовать вьюхи с одинаковыми id, либо делать через findViewById
     */
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        countBind++
        Log.d("logAdapter", "onBindViewHolder $countBind")
        val item = employeeList[holder.adapterPosition]
        when (holder.javaClass) {
            ShortViewHolder::class.java -> {
                onBindShortViewHolder(holder as ShortViewHolder, item)
            }
            InfoViewHolder::class.java -> {
                onBindInfoViewHolder(holder as InfoViewHolder, item)
            }
            ButtonViewHolder::class.java -> {
                onBindButtonViewHolder(holder as ButtonViewHolder, item)
            }
            ImageViewHolder::class.java -> {
                onBindImageViewHolder(holder as ImageViewHolder, item)
            }
            ListViewHolder::class.java -> {
                onBindListViewHolder(holder as ListViewHolder, item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("logDifferent", "getItemViewType ${position} ${employeeList[position].type}")
        return employeeList[position].type
    }


    private fun onBindShortViewHolder(holder: ShortViewHolder, item: Employee) {
        holder.shortView.itemShortName.text = item.firstName
        holder.shortView.itemShortSurname.text = item.surname
    }

    private fun onBindInfoViewHolder(holder: InfoViewHolder, item: Employee) {
        holder.infoView.itemInfoName.text = item.firstName
        holder.infoView.itemInfoSurname.text = item.surname
        holder.infoView.itemInfoBirthday.text = item.birthday
        holder.infoView.itemInfoSpecialty.text = item.specialty
        holder.infoView.itemInfoDateOfEmployment.text = item.dateOfEmployment
        holder.infoView.itemInfoOffice.text = item.office
        val avPath: String = if (item.avatarPath.isNullOrEmpty()) {
            "file:///android_asset/avatars/empty_man.jpg"
        } else {
            item.avatarPath
        }

        Picasso.get()
//            .load(employee.avatarPath)
            .load(avPath)
            .placeholder(R.drawable.empty_man)
            .fit()
            .priority(Picasso.Priority.NORMAL)
            .into(holder.infoView.itemInfoAvatarImage)

    }

    private fun onBindButtonViewHolder(holder: ButtonViewHolder, item: Employee) {

        holder.buttonView.itemButtonName.text = SpannableStringBuilder(item.firstName)
        holder.buttonView.itemButtonSurname.text = SpannableStringBuilder(item.surname)

        holder.buttonView.itemButtonEditButton.setOnClickListener {
            if (holder.buttonView.itemButtonCancelButton.visibility == View.GONE) {
                holder.buttonView.itemButtonSurname.isEnabled = true
                holder.buttonView.itemButtonInputLayoutName.isEnabled = true
                holder.buttonView.itemButtonInputLayoutSurname.endIconMode =
                    TextInputLayout.END_ICON_CLEAR_TEXT
                holder.buttonView.itemButtonEditButton.text =
                    holder.buttonView.context.getString(R.string.button_stop_edit_label)
                holder.buttonView.itemButtonCancelButton.visibility = View.VISIBLE
            } else {
                holder.buttonView.itemButtonSurname.isEnabled = false
                holder.buttonView.itemButtonInputLayoutName.isEnabled = false
                holder.buttonView.itemButtonInputLayoutSurname.endIconMode =
                    TextInputLayout.END_ICON_NONE
                holder.buttonView.itemButtonEditButton.text =
                    holder.buttonView.context.getString(R.string.button_edit_label)
                holder.buttonView.itemButtonCancelButton.visibility = View.GONE
            }


        }
        holder.buttonView.itemButtonCancelButton.setOnClickListener {
            holder.buttonView.itemButtonName.text = SpannableStringBuilder(item.firstName)
            holder.buttonView.itemButtonSurname.text = SpannableStringBuilder(item.surname)
            holder.buttonView.itemButtonSurname.isEnabled = false
            holder.buttonView.itemButtonInputLayoutName.isEnabled = false
            holder.buttonView.itemButtonInputLayoutSurname.endIconMode =
                TextInputLayout.END_ICON_NONE
            holder.buttonView.itemButtonEditButton.text =
                holder.buttonView.context.getString(R.string.button_edit_label)
            holder.buttonView.itemButtonCancelButton.visibility = View.GONE
        }

    }

    private fun onBindImageViewHolder(holder: ImageViewHolder, item: Employee) {
        val avPath: String = if (item.avatarPath.isNullOrEmpty()) {
            "file:///android_asset/avatars/empty_man.jpg"
        } else {
            item.avatarPath
        }

        Picasso.get()
//            .load(employee.avatarPath)
            .load(avPath)
            .placeholder(R.drawable.empty_man)
            .fit()
            .priority(Picasso.Priority.NORMAL)
            .into(holder.imageView.itemImageAvatarImage)
        holder.imageView.itemImageName.text = item.firstName
        holder.imageView.itemImageSurname.text = item.surname
    }

    private fun onBindListViewHolder(holder: ListViewHolder, item: Employee) {
        holder.listView.itemListName.text = item.firstName
        holder.listView.itemListSurname.text = item.surname
        holder.listView.itemListSpecialty.text = item.specialty
        val adapter =
            DifferentItemTypeListViewRecyclerAdapter(
                item.skills,
                this,
                item.id,
                holder.adapterPosition
            )
        val layoutManager = LinearLayoutManager(holder.listView.context)
//        layoutManager.orientation = RecyclerView.HORIZONTAL

        holder.listView.itemListRecyclerView.layoutManager = layoutManager
        holder.listView.itemListRecyclerView.adapter = adapter
        holder.listView.itemListCardView.setOnClickListener {
            if (holder.listView.itemListRecyclerView.visibility == View.VISIBLE) {
                isOpenNestedRecyclerView.put(item.id, false)
                holder.listView.itemListRecyclerView.visibility = View.GONE
                holder.listView.itemListExpandImageView.setImageDrawable(
                    holder.listView.itemListExpandImageView.context.getDrawable(
                        R.drawable.ic_expand_more_black_24dp
                    )
                )
            } else {
                isOpenNestedRecyclerView.put(item.id, true)
                holder.listView.itemListRecyclerView.visibility = View.VISIBLE
                holder.listView.itemListExpandImageView.setImageDrawable(
                    holder.listView.itemListExpandImageView.context.getDrawable(
                        R.drawable.ic_expand_less_black_24dp
                    )
                )
            }
        }
        if (isOpenNestedRecyclerView.get(item.id)) {
            holder.listView.itemListRecyclerView.visibility = View.VISIBLE
            holder.listView.itemListExpandImageView.setImageDrawable(
                holder.listView.itemListExpandImageView.context.getDrawable(
                    R.drawable.ic_expand_less_black_24dp
                )
            )
        } else {
            holder.listView.itemListRecyclerView.visibility = View.GONE
            holder.listView.itemListExpandImageView.setImageDrawable(
                holder.listView.itemListExpandImageView.context.getDrawable(
                    R.drawable.ic_expand_more_black_24dp
                )
            )
        }
        addSkillsToChipGroup(holder, item)
        for (skill in item.skills) {
            Log.d("logListView", "${item.firstName} ${skill}")
        }
    }

    private fun addSkillsToChipGroup(holder: ListViewHolder, item: Employee) {
        holder.listView.itemListSkillsChipGroup.removeAllViews()
        Log.d("logListView2", "addSkillsToChipGroup ")
        val skills = favoritesSkills.get(item.id)
        val skillsMapped = holder.listView.itemListSkillsChipGroup.children.toList()
        val skillTexts = ArrayList<String>()
        for (view in skillsMapped) {
            skillTexts.add((view as Chip).text.toString())
        }
        if (!skills.isNullOrEmpty()) {
            mainLoop@ for (skill in skills) {
                for (skillText in skillTexts) {
                    Log.d("logListView2", "skillText ${skillText} skill $skill")
                    if (skill == skillText) {
                        Log.d("logListView2", "skill == skillText")
                        continue@mainLoop
                    }
                }
                val chip = createChip(
                    holder.listView.context,
                    skill,
                    item.id,
                    holder.adapterPosition
                )

                holder.listView.itemListSkillsChipGroup.addView(chip)
            }
        }
        Log.d("logListView2", "addSkillsToChipGroup2 ")
    }

    private fun createChip(context: Context, text: String, itemId: Int, position: Int): Chip {
        val chip = Chip(context)
        chip.text = text
        chip.chipIcon = context.getDrawable(R.drawable.ic_book_black_24dp)
        chip.closeIcon = context.getDrawable(R.drawable.ic_cancel_black_24dp)
        chip.isCloseIconVisible = true
        chip.closeIconSize = Utils.dpToPx(context, 24f)
        chip.setOnCloseIconClickListener {
            deleteFavoriteSkill(it, itemId, position)
        }
        return chip
    }

    private fun deleteFavoriteSkill(view: View, itemId: Int, position: Int) {
        val chip = view as Chip
        val chipText = chip.text.toString()

        val fS = favoritesSkills.get(itemId)
        fS.remove(chipText)
        favoritesSkills.remove(itemId)
        favoritesSkills.put(itemId, fS)

        Log.d("logListView", "deleteFavoriteSkill ${chip.text}")
        val chipGroup = chip.parent as ChipGroup
        chipGroup.removeView(view)
        notifyItemChanged(position)
        Log.d("logListView", "deleteFavoriteSkill ${chip.text}")
    }

    override fun addSkill(skill: String, employeeId: Int, parentAdapterPosition: Int) {
        Log.d("logListView", "addSkill $employeeId $skill")
        val sk = favoritesSkills.get(employeeId)
        if (sk.contains(skill)) {
            Log.d("logListView3", " такой чип уже есть")
            return
        }
        sk.add(skill)
        favoritesSkills.delete(employeeId)
        favoritesSkills.put(employeeId, sk)
        notifyItemChanged(parentAdapterPosition)
    }

    abstract class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    class ShortViewHolder(val shortView: View) : BaseViewHolder(shortView)
    class InfoViewHolder(val infoView: View) : BaseViewHolder(infoView)
    class ButtonViewHolder(val buttonView: View) : BaseViewHolder(buttonView)
    class ImageViewHolder(val imageView: View) : BaseViewHolder(imageView)
    class ListViewHolder(val listView: View) : BaseViewHolder(listView)
}