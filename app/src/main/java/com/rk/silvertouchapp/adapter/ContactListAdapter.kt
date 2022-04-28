package com.rahilkarim.skpust.ui.BusinessDetailFrag

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rk.silvertouchapp.databinding.CategoryListItemBinding
import com.rk.silvertouchapp.databinding.ContactListItemBinding
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact
import java.util.HashMap


class ContactListAdapter(
    private val activity: Context,
    private val list: ArrayList<Contact>,
    private val onClick: contactListAdapterOnClick
) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>() {

    var tag = "ContactListAdapter"
    lateinit var binding: ContactListItemBinding

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        binding = ContactListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = list[position]

        binding.profileImage.setImageURI(Uri.parse(model.profileImage))
        binding.categoryName.text = model.categoryName

        binding.ivEdit.setOnClickListener {

            onClick.editContact(position,model)
        }

        binding.ivDelete.setOnClickListener {

            onClick.removeContact(position,model)
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

    interface contactListAdapterOnClick {

        fun editContact(pos: Int, model: Contact)
        fun removeContact(pos: Int, model: Contact)
    }
}