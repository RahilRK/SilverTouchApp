package com.rk.silvertouchapp.ui.fragment.addCategory

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rahilkarim.skpust.ui.BusinessDetailFrag.CategoryListAdapter
import com.rk.silvertouchapp.R
import com.rk.silvertouchapp.databinding.FragmentAddCategoryBinding
import com.rk.silvertouchapp.model.Category
import com.rk.silvertouchapp.ui.MainActivity
import com.rk.silvertouchapp.util.Application
import com.rk.silvertouchapp.util.GlobalClass
import com.rk.silvertouchapp.util.Repository

class AddCategoryFragment : Fragment() {

    var TAG = this.javaClass.simpleName
    private lateinit var activity: Context

    lateinit var binding : FragmentAddCategoryBinding
    lateinit var globalClass: GlobalClass
    lateinit var repository: Repository
    lateinit var viewModel: CategoryFragVM

    val toolbar : androidx.appcompat.widget.Toolbar get() = binding.toolbar
    val categoryNameEd : TextInputEditText get() = binding.categoryNameEd
    val saveCategorybt : Button get() = binding.saveCategorybt
    val recyclerView : RecyclerView get() = binding.recyclerView

    private var arrayList = arrayListOf<Category>()
    lateinit var adapter: CategoryListAdapter

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
        binding = FragmentAddCategoryBinding.inflate(layoutInflater,container,false)

        init()
        setToolbar()
        onClick()
        observeData()

        return binding.root
    }

    private fun init() {
        globalClass = (requireActivity().application as Application).globalClass
        repository = (requireActivity().application as Application).repository
        viewModel = ViewModelProvider(this, CategoryFragVMFactory(repository))
            .get(CategoryFragVM::class.java)
    }

    fun setToolbar() {
        toolbar.title = resources.getString(R.string.add_category)
    }

    private fun onClick() {

        saveCategorybt.setOnClickListener {

            if(isValidate()) {
                viewModel.addCategory(Category(categoryName = categoryNameEd.text.toString()))
                categoryNameEd.text!!.clear()
                globalClass.toastlong("Category added successfully")
            }
        }
    }

    private fun isValidate():Boolean {

        if(categoryNameEd.text.isNullOrEmpty()) {
            globalClass.toastlong("Category name should not be empty")
            return false
        }
        else if(categoryNameEd.text!!.length < 3) {
            globalClass.toastlong("Category name should contain atleast 3 characters")
            return false
        }

        return true
    }

    private fun observeData() {

        viewModel.categoryList.observe(viewLifecycleOwner) { list ->

            arrayList = list as ArrayList<Category>

            if(arrayList.isNotEmpty()) {
                setAdapter(arrayList)
            }
        }
    }

    private fun setAdapter(arrayList: ArrayList<Category>) {

        recyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false)
        adapter = CategoryListAdapter(activity, arrayList, object : CategoryListAdapter.categoryListAdapterOnClick {

            override fun editCategory(pos: Int, model: Category) {

            }

            override fun removeCategory(pos: Int, model: Category) {

                adapter.removeItem(pos)
                viewModel.deleteCategory(model.id.toInt())
                globalClass.toastshort("Category removed successfully")
            }
        })

        recyclerView.adapter = adapter
    }
}