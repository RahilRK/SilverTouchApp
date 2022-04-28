package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rk.silvertouchapp.databinding.CategoryListItemBinding
import com.rk.silvertouchapp.model.Category
import java.util.HashMap


class CategoryListAdapter(
    private val activity: Context,
    private val list: ArrayList<Category>,
    private val onClick: categoryListAdapterOnClick
) : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    var tag = "categoryListAdapter"
    lateinit var binding: CategoryListItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = CategoryListItemBinding.inflate(
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

        binding.ivEdit.setOnClickListener {

            onClick.editCategory(position,model)
        }

        binding.ivDelete.setOnClickListener {

            onClick.removeCategory(position,model)
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

    interface categoryListAdapterOnClick {

        fun editCategory(pos: Int, model: Category)
        fun removeCategory(pos: Int, model: Category)
    }
}