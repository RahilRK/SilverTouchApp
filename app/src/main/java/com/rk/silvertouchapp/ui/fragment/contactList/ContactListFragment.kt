package com.rk.silvertouchapp.ui.fragment.contactList

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rahilkarim.skpust.ui.BusinessDetailFrag.ContactListAdapter
import com.rahilkarim.skpust.ui.BusinessDetailFrag.FilterCategoryListAdapter
import com.rk.silvertouchapp.R
import com.rk.silvertouchapp.databinding.FragmentContactListBinding
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact
import com.rk.silvertouchapp.ui.MainActivity
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository

class ContactListFragment : Fragment() {

    var TAG = this.javaClass.simpleName
    private lateinit var activity: Context

    lateinit var binding : FragmentContactListBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: ContactListFragVM

    val toolbar : androidx.appcompat.widget.Toolbar get() = binding.toolbar
    val recyclerView : RecyclerView get() = binding.recyclerView

    private var arrayList = arrayListOf<Contact>()
    lateinit var adapter: ContactListAdapter

    var menu_search: MenuItem? = null
    var searchView: SearchView? = null

    var categoryList: java.util.ArrayList<Category> = java.util.ArrayList<Category>()
    lateinit var filterCategoryAdapter: FilterCategoryListAdapter

    var selectedCategoryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {

            (activity as MainActivity?)?.setToolbar(toolbar)
        }
        catch (e: Exception) {

            val error = Log.getStackTraceString(e)
            globalClass.log(TAG,error)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentContactListBinding.inflate(layoutInflater,container,false)

        init()
        setToolbar()
        observeData()
        observeCategoryList()

        return binding.root
    }

    private fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
        viewModel = ViewModelProvider(this, ContactListFragVMFactory(repository))
            .get(ContactListFragVM::class.java)
    }

    fun setToolbar() {
        toolbar.title = resources.getString(R.string.contact_list)
    }

    private fun observeData() {

        viewModel.contactList.observe(viewLifecycleOwner) { list ->

            arrayList = list as ArrayList<Contact>

            if(arrayList.isNotEmpty()) {
                setAdapter(arrayList)
            }
        }
    }

    private fun setAdapter(arrayList: ArrayList<Contact>) {

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false)
        adapter = ContactListAdapter(activity, arrayList, object : ContactListAdapter.contactListAdapterOnClick {
            override fun editContact(pos: Int, model: Contact) {

                val action = ContactListFragmentDirections.actionContactListFragmentToEditContactFragment(model)
                findNavController().navigate(action)
            }

            override fun removeContact(pos: Int, model: Contact) {

                adapter.removeItem(pos)
                viewModel.deleteContact(model.id.toInt())
                globalClass.toastshort("Contact removed successfully")
            }
        })

        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contactlist_optionmenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        menu_search = menu.findItem(R.id.menu_search_contact)
        searchView = menu_search?.actionView as SearchView
        searchView!!.queryHint = "Enter name"
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if (s.isNotEmpty()) {
                    getSearchData(s)
                } else {
                    globalClass.toastshort("Enter name")
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                if (s.isNotEmpty()) {
                    getSearchData(s)
                } else {
                    observeData()
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        return when (item.itemId) {
            R.id.menu_filter_contact -> {

                showFilterBottomsheet()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun getSearchData(keyword: String) {

        viewModel.searchContactList(keyword).observe(
            viewLifecycleOwner
        ) {
            arrayList.clear()
            arrayList = it as ArrayList<Contact>
            if (!arrayList.isEmpty()) {
                setAdapter(arrayList)
            } else {
                globalClass.toastshort("No data found")
            }
        }
    }

    private fun observeCategoryList() {

        viewModel.categoryList.observe(viewLifecycleOwner) { list ->

            categoryList = list as ArrayList<Category>
        }

    }

    private fun showFilterBottomsheet() {

        val dialog: Dialog = BottomSheetDialog(activity)
        dialog.setContentView(R.layout.categorylist_bs)

        // set the custom dialog components - text, image and button
        val categoryRecyclerview: RecyclerView = dialog.findViewById(R.id.categoryRecyclerview)
        categoryRecyclerview.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false)
        filterCategoryAdapter = FilterCategoryListAdapter(activity, selectedCategoryName, categoryList, object: FilterCategoryListAdapter.filterCategoryListAdapterOnClick {

            override fun onClick(pos: Int, model: Category) {

                selectedCategoryName = model.categoryName
                dialog.dismiss()
                getFilter(selectedCategoryName)
            }
        })

        categoryRecyclerview.adapter = filterCategoryAdapter

        dialog.show()
    }

    fun getFilter(filterKeyword: String) {

        viewModel.filterContactList(filterKeyword).observe(
            viewLifecycleOwner
        ) {
            arrayList.clear()
            arrayList = it as ArrayList<Contact>
            if (!arrayList.isEmpty()) {
                setAdapter(arrayList)
            } else {
                globalClass.toastshort("No data found")
            }
        }
    }
}