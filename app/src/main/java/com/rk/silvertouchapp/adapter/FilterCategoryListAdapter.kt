package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rk.silvertouchapp.databinding.FilterCategoryListItemBinding
import com.rk.silvertouchapp.model.Category


class FilterCategoryListAdapter(
    private val activity: Context,
    private val selectedCategoryName: String,
    private val list: ArrayList<Category>,
    private val onClick: filterCategoryListAdapterOnClick
) : RecyclerView.Adapter<FilterCategoryListAdapter.ViewHolder>() {

    var tag = "FilterCategoryListAdapter"
    lateinit var binding: FilterCategoryListItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = FilterCategoryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]

        binding.categoryName.text = model.categoryName

        binding.categoryName.setOnClickListener {

            onClick.onClick(position,model)
        }

        if (selectedCategoryName != "" &&
            model.categoryName == selectedCategoryName) {
            binding.categoryName.setChecked(true)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return list.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
    }

    interface filterCategoryListAdapterOnClick {

        fun onClick(pos: Int, model: Category)
    }
}