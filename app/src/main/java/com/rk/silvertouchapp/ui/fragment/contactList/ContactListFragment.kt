package com.rk.silvertouchapp.ui.fragment.contactList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahilkarim.skpust.ui.BusinessDetailFrag.CategoryListAdapter
import com.rahilkarim.skpust.ui.BusinessDetailFrag.ContactListAdapter
import com.rk.silvertouchapp.databinding.FragmentContactListBinding
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.model.Contact
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = requireContext()
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

        return binding.root
    }

    private fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
        viewModel = ViewModelProvider(this, ContactListFragVMFactory(repository))
            .get(ContactListFragVM::class.java)
    }

    fun setToolbar() {
//        toolbar.title = resources.getString(R.string.add_image)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        toolbar.setNavigationOnClickListener {
//            getActivity()?.onBackPressed()
//        }
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

                val action = ContactListFragmentDirections.actionContactListFragmentToEditContactFragment3(model)
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
}